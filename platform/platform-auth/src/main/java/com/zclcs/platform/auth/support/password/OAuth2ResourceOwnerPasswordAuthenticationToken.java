package com.zclcs.platform.auth.support.password;

import com.zclcs.platform.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @author zclcs
 * @description 密码授权token信息
 */
public class OAuth2ResourceOwnerPasswordAuthenticationToken extends OAuth2ResourceOwnerBaseAuthenticationToken {

    public OAuth2ResourceOwnerPasswordAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                                          Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }

}
