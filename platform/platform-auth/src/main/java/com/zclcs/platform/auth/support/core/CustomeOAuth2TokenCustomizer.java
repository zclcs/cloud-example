package com.zclcs.platform.auth.support.core;

import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.security.starter.entity.SecurityUser;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * token 输出增强
 *
 * @author zclcs
 */
public class CustomeOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

    /**
     * Customize the OAuth 2.0 Token attributes.
     *
     * @param context the context containing the OAuth 2.0 Token attributes
     */
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
        OAuth2TokenClaimsSet.Builder claims = context.getClaims();
        claims.claim(SecurityConstant.DETAILS_LICENSE, SecurityConstant.PROJECT_LICENSE);
        String clientId = context.getAuthorizationGrant().getName();
        claims.claim(SecurityConstant.CLIENT_ID, clientId);
        // 客户端模式不返回具体用户信息
        if (SecurityConstant.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType().getValue())) {
            return;
        }

        SecurityUser securityUser = (SecurityUser) context.getPrincipal().getPrincipal();
        claims.claim(SecurityConstant.DETAILS_USER, securityUser);
    }

}
