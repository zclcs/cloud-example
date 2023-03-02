package com.zclcs.platform.auth.endpoint;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.TemporalAccessorUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.constant.OAuth2ErrorCodesExpand;
import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.utils.SpringContextHolderUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.common.security.starter.annotation.Inner;
import com.zclcs.common.security.starter.exception.OAuthClientException;
import com.zclcs.common.security.starter.utils.OAuth2EndpointUtil;
import com.zclcs.platform.auth.support.handler.MyAuthenticationFailureEventHandler;
import com.zclcs.platform.system.api.entity.vo.OauthClientDetailsVo;
import com.zclcs.platform.system.api.entity.vo.TokenVo;
import com.zclcs.platform.system.api.fegin.RemoteClientDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zclcs
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class MyTokenEndpoint {

    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    private final RemoteClientDetailsService remoteClientDetailsService;

    private final RedisService redisService;

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    /**
     * 认证页面
     *
     * @param modelAndView
     * @param error        表单登录失败处理回调的错误信息
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("ftl/login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @GetMapping("/confirm_access")
    public ModelAndView confirm(Principal principal, ModelAndView modelAndView,
                                @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                @RequestParam(OAuth2ParameterNames.STATE) String state) {
        OauthClientDetailsVo clientDetails = Optional.ofNullable(remoteClientDetailsService.getClientDetailsById(clientId).getData())
                .orElseThrow(() -> new OAuthClientException("clientId 不合法"));

        Set<String> authorizedScopes = StringUtils.commaDelimitedListToSet(clientDetails.getScope());
        modelAndView.addObject("clientId", clientId);
        modelAndView.addObject("state", state);
        modelAndView.addObject("scopeList", authorizedScopes);
        modelAndView.addObject("principalName", principal.getName());
        modelAndView.setViewName("ftl/confirm");
        return modelAndView;
    }

    /**
     * 退出并删除token
     *
     * @param authHeader Authorization
     */
    @DeleteMapping("/logout")
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
    public void checkToken(String token, HttpServletResponse response, HttpServletRequest request) {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

        AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureEventHandler(objectMapper, rabbitTemplate);
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
     * @param params 分页参数
     * @return
     */
    @Inner
    @PostMapping("/page")
    public BaseRsp<BasePage<TokenVo>> tokenList(@RequestBody Map<String, Object> params) {
        // 根据分页参数获取对应数据
        String key = String.format("%s::*", RedisCachePrefixConstant.PROJECT_OAUTH_ACCESS);
        int pageSize = MapUtil.getInt(params, MyConstant.PAGE_SIZE);
        int pageNum = MapUtil.getInt(params, MyConstant.PAGE_NUM);
        Set<Object> keys = redisService.sGet(key);
        List<String> pages = keys.stream().map(Object::toString).skip((long) (pageSize - 1) * pageNum).limit(pageNum).collect(Collectors.toList());
        BasePage<TokenVo> basePage = new BasePage<>(pageNum, pageSize);

        List<TokenVo> tokenVoList = redisService.multiGet(pages).stream().map(obj -> {
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
        basePage.setRecords(tokenVoList);
        basePage.setTotal(keys.size());
        return RspUtil.data(basePage);
    }

}
