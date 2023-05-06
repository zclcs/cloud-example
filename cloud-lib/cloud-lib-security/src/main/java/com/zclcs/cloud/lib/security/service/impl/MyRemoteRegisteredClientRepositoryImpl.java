package com.zclcs.cloud.lib.security.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.fegin.RemoteClientDetailsService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/**
 * 查询客户端相关信息实现
 *
 * @author zclcs
 */
@RequiredArgsConstructor
public class MyRemoteRegisteredClientRepositoryImpl implements RegisteredClientRepository {

    private final RemoteClientDetailsService clientDetailsService;

    /**
     * Saves the registered client.
     *
     * <p>
     * IMPORTANT: Sensitive information should be encoded externally from the
     * implementation, e.g. {@link RegisteredClient#getClientSecret()}
     *
     * @param registeredClient the {@link RegisteredClient}
     */
    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the registered client identified by the provided {@code id}, or
     * {@code null} if not found.
     *
     * @param id the registration identifier
     * @return the {@link RegisteredClient} if found, otherwise {@code null}
     */
    @Override
    public RegisteredClient findById(String id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the registered client identified by the provided {@code clientId}, or
     * {@code null} if not found.
     * @param clientId the client identifier
     * @return the {@link RegisteredClient} if found, otherwise {@code null}
     */

    /**
     * 重写原生方法支持redis缓存
     *
     * @param clientId
     * @return
     */
    @Override
    public RegisteredClient findByClientId(String clientId) {
        OauthClientDetails oauthClientDetailsCache = SystemCacheUtil.getOauthClientDetailsCache(clientId);
        if (oauthClientDetailsCache == null || StrUtil.isBlank(oauthClientDetailsCache.getClientId())) {
            throw new OAuth2AuthorizationCodeRequestAuthenticationException(
                    new OAuth2Error("客户端查询异常，请检查数据库链接"), null);
        }

        RegisteredClient.Builder builder = RegisteredClient.withId(oauthClientDetailsCache.getClientId())
                .clientId(oauthClientDetailsCache.getClientId())
                .clientSecret(Security.NOOP + oauthClientDetailsCache.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

        // 授权模式
        Optional.ofNullable(oauthClientDetailsCache.getAuthorizedGrantTypes())
                .ifPresent(grants -> StringUtils.commaDelimitedListToSet(grants)
                        .forEach(s -> builder.authorizationGrantType(new AuthorizationGrantType(s))));
        // 回调地址
        Optional.ofNullable(oauthClientDetailsCache.getWebServerRedirectUri()).ifPresent(redirectUri -> Arrays
                .stream(redirectUri.split(StrUtil.COMMA)).filter(StrUtil::isNotBlank).forEach(builder::redirectUri));

        // scope
        Optional.ofNullable(oauthClientDetailsCache.getScope()).ifPresent(
                scope -> Arrays.stream(scope.split(StrUtil.COMMA)).filter(StrUtil::isNotBlank).forEach(builder::scope));

        return builder
                .tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofSeconds(Optional
                                .ofNullable(oauthClientDetailsCache.getAccessTokenValidity()).orElse(CommonCore.ACCESS_TOKEN_VALIDITY_SECONDS)))
                        .refreshTokenTimeToLive(
                                Duration.ofSeconds(Optional.ofNullable(oauthClientDetailsCache.getRefreshTokenValidity())
                                        .orElse(CommonCore.REFRESH_TOKEN_VALIDITY_SECONDS)))
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(!BooleanUtil.toBoolean(oauthClientDetailsCache.getAutoapprove())).build())
                .build();

    }

}
