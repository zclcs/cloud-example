package com.zclcs.platform.auth.support.core;

import com.zclcs.common.core.constant.Security;
import com.zclcs.common.security.entity.SecurityUser;
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
        claims.claim(Security.DETAILS_LICENSE, Security.PROJECT_LICENSE);
        String clientId = context.getAuthorizationGrant().getName();
        claims.claim(Security.CLIENT_ID, clientId);
        // 客户端模式不返回具体用户信息
        if (Security.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType().getValue())) {
            return;
        }

        SecurityUser securityUser = (SecurityUser) context.getPrincipal().getPrincipal();
        claims.claim(Security.DETAILS_USER, securityUser);
    }

}
