package com.zclcs.platform.auth.support.handler;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.common.core.constant.DictConstant;
import com.zclcs.common.core.constant.RabbitConstant;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.core.utils.I18nUtil;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.rabbitmq.starter.entity.MessageStruct;
import com.zclcs.common.security.starter.utils.LoginLogUtil;
import com.zclcs.platform.system.api.entity.ao.LoginLogAo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Optional;

/**
 * @author zclcs
 */
@Slf4j
@RequiredArgsConstructor
public class MyAuthenticationFailureEventHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     *                  request.
     */
    @SneakyThrows(IOException.class)
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        String username = Optional.ofNullable(request.getParameter(OAuth2ParameterNames.USERNAME)).orElse("未填写");

        log.info("用户：{} 登录失败，异常：{}", username, exception.getLocalizedMessage());
        log.error(exception.getMessage(), exception);
        LoginLogAo loginLog = LoginLogUtil.getLoginLog();
        loginLog.setUsername(username);
        loginLog.setLoginType(DictConstant.LOGIN_LOG_LOGIN_TYPE_02);
        // 发送异步日志事件
        loginLog.setCreateBy(username);
        loginLog.setUpdateBy(username);
        rabbitTemplate.convertAndSend(RabbitConstant.DIRECT_EXCHANGE, RabbitConstant.SYSTEM_LOGIN_LOG_ROUTE_KEY, MessageStruct.builder().message(objectMapper.writeValueAsString(loginLog)).build());
        // 写出错误信息
        sendErrorResponse(request, response, exception);
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,
                                   AuthenticationException exception) throws IOException {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        String errorMessage;

        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2AuthenticationException authorizationException = (OAuth2AuthenticationException) exception;
            errorMessage = StrUtil.isBlank(authorizationException.getError().getDescription())
                    ? authorizationException.getError().getErrorCode()
                    : authorizationException.getError().getDescription();
        } else {
            errorMessage = exception.getLocalizedMessage();
        }

        // 手机号登录
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (SecurityConstant.APP.equals(grantType)) {
            errorMessage = I18nUtil.getSecurityMessage("AbstractUserDetailsAuthenticationProvider.smsBadCredentials");
        }

        MappingJackson2HttpMessageConverter errorHttpResponseConverter = new MappingJackson2HttpMessageConverter();
        errorHttpResponseConverter.write(RspUtil.message(errorMessage), MediaType.APPLICATION_JSON, httpResponse);
    }

}
