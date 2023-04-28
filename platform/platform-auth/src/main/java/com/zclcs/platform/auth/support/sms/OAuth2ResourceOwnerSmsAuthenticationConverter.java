package com.zclcs.platform.auth.support.sms;

import com.zclcs.common.core.constant.Security;
import com.zclcs.common.security.utils.OAuth2EndpointUtil;
import com.zclcs.platform.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author zclcs
 * <p>
 * 短信登录转换器
 */
public class OAuth2ResourceOwnerSmsAuthenticationConverter
        extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerSmsAuthenticationToken> {

    /**
     * 是否支持此convert
     *
     * @param grantType 授权类型
     * @return
     */
    @Override
    public boolean support(String grantType) {
        return Security.APP.equals(grantType);
    }

    @Override
    public OAuth2ResourceOwnerSmsAuthenticationToken buildToken(Authentication clientPrincipal, Set requestedScopes,
                                                                Map additionalParameters) {
        return new OAuth2ResourceOwnerSmsAuthenticationToken(new AuthorizationGrantType(Security.APP),
                clientPrincipal, requestedScopes, additionalParameters);
    }

    /**
     * 校验扩展参数 密码模式密码必须不为空
     *
     * @param request 参数列表
     */
    @Override
    public void checkParams(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = OAuth2EndpointUtil.getParameters(request);
        // PHONE (REQUIRED)
        String phone = parameters.getFirst(Security.SMS_PARAMETER_NAME);
        if (!StringUtils.hasText(phone) || parameters.get(Security.SMS_PARAMETER_NAME).size() != 1) {
            OAuth2EndpointUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, Security.SMS_PARAMETER_NAME,
                    OAuth2EndpointUtil.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
    }

}
