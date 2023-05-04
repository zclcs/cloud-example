package com.zclcs.platform.auth.support.handler;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.common.core.constant.Dict;
import com.zclcs.common.core.constant.RabbitMq;
import com.zclcs.common.core.constant.Security;
import com.zclcs.common.core.enums.ExchangeType;
import com.zclcs.common.rabbitmq.starter.entity.MessageStruct;
import com.zclcs.common.rabbitmq.starter.properties.MyRabbitMqProperties;
import com.zclcs.common.rabbitmq.starter.utils.RabbitKeyUtil;
import com.zclcs.common.security.entity.SecurityUser;
import com.zclcs.common.security.utils.LoginLogUtil;
import com.zclcs.platform.system.api.entity.ao.LoginLogAo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author zclcs
 */
@Slf4j
@RequiredArgsConstructor
public class MyAuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final MyRabbitMqProperties myRabbitMqProperties;

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     */
    @SneakyThrows(value = IOException.class)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
        if (MapUtil.isNotEmpty(map)) {
            // 发送异步日志事件
            SecurityUser userInfo = (SecurityUser) map.get(Security.DETAILS_USER);
            log.info("用户：{} 登录成功", userInfo.getName());
            SecurityContextHolder.getContext().setAuthentication(accessTokenAuthentication);
            LoginLogAo loginLog = LoginLogUtil.getLoginLog();
            loginLog.setUsername(userInfo.getName());
            loginLog.setLoginType(Dict.LOGIN_LOG_LOGIN_TYPE_01);
            // 发送异步日志事件
            loginLog.setCreateBy(userInfo.getName());
            loginLog.setUpdateBy(userInfo.getName());
            rabbitTemplate.convertAndSend(RabbitKeyUtil.getExchangeName(
                            myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG).getQueueName(), ExchangeType.DIRECT), RabbitKeyUtil.getRouteKey(
                            myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG).getQueueName()),
                    MessageStruct.builder().message(objectMapper.writeValueAsString(loginLog)).build());
        }

        // 输出token
        sendAccessTokenResponse(request, response, authentication);
    }

    private void sendAccessTokenResponse(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException {

        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }
        OAuth2AccessTokenResponse accessTokenResponse = builder.build();
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

        // 无状态 注意删除 context 上下文的信息
        SecurityContextHolder.clearContext();
        HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
    }

}
