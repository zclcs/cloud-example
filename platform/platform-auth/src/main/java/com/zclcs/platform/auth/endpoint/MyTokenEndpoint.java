package com.zclcs.platform.auth.endpoint;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.TemporalAccessorUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.OAuth2ErrorCodesExpand;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.core.utils.SpringContextHolderUtil;
import com.zclcs.cloud.lib.rabbit.mq.properties.MyRabbitMqProperties;
import com.zclcs.cloud.lib.security.annotation.Inner;
import com.zclcs.cloud.lib.security.exception.OAuthClientException;
import com.zclcs.cloud.lib.security.utils.OAuth2EndpointUtil;
import com.zclcs.platform.auth.support.handler.MyAuthenticationFailureEventHandler;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.entity.vo.TokenVo;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zclcs
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
@Tag(name = "token管理")
public class MyTokenEndpoint {

    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    private final RedisTemplate<String, Object> redisTemplateJava;

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    private final MyRabbitMqProperties myRabbitMqProperties;

    /**
     * 认证页面
     *
     * @param modelAndView
     * @param error        表单登录失败处理回调的错误信息
     * @return ModelAndView
     */
    @GetMapping("/login")
    @Operation(summary = "认证页面")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @GetMapping("/confirm_access")
    @Operation(summary = "确认页面")
    public ModelAndView confirm(Principal principal, ModelAndView modelAndView,
                                @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                @RequestParam(OAuth2ParameterNames.STATE) String state) {
        OauthClientDetails oauthClientDetailsCache = SystemCacheUtil.getOauthClientDetailsCache(clientId);
        Optional.ofNullable(oauthClientDetailsCache).map(OauthClientDetails::getClientId).filter(StrUtil::isNotBlank)
                .orElseThrow(() -> new OAuthClientException("clientId 不合法"));

        Set<String> authorizedScopes = StringUtils.commaDelimitedListToSet(oauthClientDetailsCache.getScope());
        modelAndView.addObject("clientId", clientId);
        modelAndView.addObject("state", state);
        modelAndView.addObject("scopeList", authorizedScopes);
        modelAndView.addObject("principalName", principal.getName());
        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    /**
     * 退出并删除token
     *
     * @param authHeader Authorization
     */
    @DeleteMapping("/logout")
    @Operation(summary = "退出并删除token")
    @Parameters({
            @Parameter(name = "authHeader", description = "Authorization", required = false, in = ParameterIn.HEADER)
    })
    public BaseRsp<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StrUtil.isBlank(authHeader)) {
            return RspUtil.data(Boolean.FALSE);
        }

        String tokenValue = authHeader.replace(OAuth2AccessToken.TokenType.BEARER.getValue(), StrUtil.EMPTY).trim();
        return removeToken(tokenValue);
    }

    /**
     * 校验token
     *
     * @param token 令牌
     */
    @SneakyThrows(value = {IOException.class, ServletException.class})
    @GetMapping("/check_token")
    @Operation(summary = "校验token")
    @Parameters({
            @Parameter(name = "token", description = "token", required = true, in = ParameterIn.QUERY)
    })
    public void checkToken(@RequestParam String token, HttpServletResponse response, HttpServletRequest request) {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

        AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureEventHandler(objectMapper, rabbitTemplate, myRabbitMqProperties);
        if (StrUtil.isBlank(token)) {
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new InvalidBearerTokenException(OAuth2ErrorCodesExpand.TOKEN_MISSING));
            return;
        }
        OAuth2Authorization authorization = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);

        // 如果令牌不存在 返回401
        if (authorization == null || authorization.getAccessToken() == null) {
            authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new InvalidBearerTokenException(OAuth2ErrorCodesExpand.INVALID_BEARER_TOKEN));
            return;
        }

        Map<String, Object> claims = authorization.getAccessToken().getClaims();
        OAuth2AccessTokenResponse sendAccessTokenResponse = OAuth2EndpointUtil.sendAccessTokenResponse(authorization,
                claims);
        this.accessTokenHttpResponseConverter.write(sendAccessTokenResponse, MediaType.APPLICATION_JSON, httpResponse);
    }

    /**
     * 令牌管理调用
     *
     * @param token token
     */
    @Inner
    @DeleteMapping("/{token}")
    @Operation(summary = "删除token")
    @Parameters({
            @Parameter(name = "token", description = "token", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<Boolean> removeToken(@PathVariable("token") String token) {
        OAuth2Authorization authorization = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization == null) {
            return RspUtil.data(Boolean.TRUE);
        }

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (accessToken == null || StrUtil.isBlank(accessToken.getToken().getTokenValue())) {
            return RspUtil.data(Boolean.TRUE);
        }
        // 清空access token
        oAuth2AuthorizationService.remove(authorization);
        // 处理自定义退出事件，保存相关日志
        SpringContextHolderUtil.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
                authorization.getPrincipalName(), authorization.getRegisteredClientId())));
        return RspUtil.data(Boolean.TRUE);
    }

    /**
     * 查询token
     *
     * @param basePageAo 分页参数
     * @return
     */
    @Inner
    @GetMapping("/page")
    @Operation(summary = "查询token（分页）")
    public BaseRsp<BasePage<TokenVo>> tokenList(@ParameterObject @Validated BasePageAo basePageAo) {
        // 根据分页参数获取对应数据
        String key = String.format("%s::*", RedisCachePrefix.PROJECT_OAUTH_ACCESS);
        int pageSize = basePageAo.getPageSize();
        int pageNum = basePageAo.getPageNum();
        Set<String> keys = redisTemplateJava.keys(key);
        List<String> pages = null;
        if (keys != null) {
            pages = keys.stream().skip((long) (pageSize - 1) * pageNum).limit(pageNum).collect(Collectors.toList());
        }
        BasePage<TokenVo> basePage = new BasePage<>(pageNum, pageSize);

        List<TokenVo> tokenVoList = null;
        if (pages != null) {
            tokenVoList = Objects.requireNonNull(redisTemplateJava.opsForValue().multiGet(pages)).stream().map(obj -> {
                OAuth2Authorization authorization = (OAuth2Authorization) obj;
                TokenVo tokenVo = new TokenVo();
                tokenVo.setClientId(authorization.getRegisteredClientId());
                tokenVo.setId(authorization.getId());
                tokenVo.setUsername(authorization.getPrincipalName());
                OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
                tokenVo.setAccessToken(accessToken.getToken().getTokenValue());

                String expiresAt = TemporalAccessorUtil.format(accessToken.getToken().getExpiresAt(),
                        DatePattern.NORM_DATETIME_PATTERN);
                tokenVo.setExpiresAt(expiresAt);

                String issuedAt = TemporalAccessorUtil.format(accessToken.getToken().getIssuedAt(),
                        DatePattern.NORM_DATETIME_PATTERN);
                tokenVo.setIssuedAt(issuedAt);
                return tokenVo;
            }).collect(Collectors.toList());
        }
        basePage.setRecords(tokenVoList);
        if (keys != null) {
            basePage.setTotal(keys.size());
        } else {
            basePage.setTotal(0);
        }
        return RspUtil.data(basePage);
    }

}
