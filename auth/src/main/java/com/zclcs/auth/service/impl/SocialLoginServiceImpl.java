package com.zclcs.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import com.zclcs.auth.manager.UserManager;
import com.zclcs.auth.properties.MyAuthProperties;
import com.zclcs.auth.service.SocialLoginService;
import com.zclcs.auth.service.SystemUserConnectionService;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.GrantTypeConstant;
import com.zclcs.common.core.constant.ParamsConstant;
import com.zclcs.common.core.constant.SocialConstant;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.entity.system.ao.BindUserAo;
import com.zclcs.common.core.entity.system.ao.SystemUserConnectionAo;
import com.zclcs.common.core.entity.system.vo.SystemUserConnectionVo;
import com.zclcs.common.core.entity.system.vo.SystemUserVo;
import com.zclcs.common.core.exception.MyException;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.utils.BaseUsersUtil;
import com.zclcs.common.core.utils.BaseUtil;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zclcs
 */
@Service
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements SocialLoginService {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String NOT_BIND = "not_bind";
    private static final String SOCIAL_LOGIN_SUCCESS = "social_login_success";

    private final UserManager userManager;
    private final AuthRequestFactory factory;
    private final MyAuthProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final SystemUserConnectionService userConnectionService;
    private final ResourceOwnerPasswordTokenGranter granter;
    private final RedisClientDetailsService redisClientDetailsService;

    @Override
    public AuthRequest renderAuth(String oauthType) {
        return factory.get(getAuthSource(oauthType));
    }

    @Override
    public BaseRsp<Object> resolveBind(String oauthType, AuthCallback callback) {
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (!response.ok()) {
            throw new MyException(String.format("第三方登录失败，%s", response.getMsg()));
        } else {
            return BaseRspUtil.message(response.getData());
        }
    }

    @Override
    public BaseRsp<Object> resolveLogin(String oauthType, AuthCallback callback) {
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            AuthUser authUser = (AuthUser) response.getData();
            SystemUserConnectionVo userConnection = userConnectionService.findSystemUserConnectionOne(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection == null) {
                return BaseRspUtil.message(NOT_BIND, authUser);
            } else {
                SystemUserVo user = userManager.findByName(userConnection.getUserName());
                if (user == null) {
                    throw new MyException("系统中未找到与第三方账号对应的账户");
                }
                OAuth2AccessToken oAuth2AccessToken = getOauth2AccessToken(user);
                return BaseRspUtil.message(SOCIAL_LOGIN_SUCCESS, oAuth2AccessToken);
            }
        } else {
            throw new MyException(String.format("第三方登录失败，%s", response.getMsg()));
        }
    }

    @Override
    public OAuth2AccessToken bindLogin(BindUserAo bindUser, AuthUser authUser) {
        SystemUserVo systemUser = userManager.findByName(bindUser.getBindUsername());
        if (systemUser == null || !passwordEncoder.matches(bindUser.getBindPassword(), systemUser.getPassword())) {
            throw new MyException("绑定系统账号失败，用户名或密码错误！");
        }
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public OAuth2AccessToken signLogin(BindUserAo registerUser, AuthUser authUser) {
        SystemUserVo user = this.userManager.findByName(registerUser.getBindUsername());
        if (user != null) {
            throw new MyException("该用户名已存在！");
        }
        String encryptPassword = passwordEncoder.encode(registerUser.getBindPassword());
        SystemUserVo systemUser = this.userManager.registerUser(registerUser.getBindUsername(), encryptPassword);
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public void bind(BindUserAo bindUser, AuthUser authUser) {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            SystemUserConnectionVo userConnection = userConnectionService.findSystemUserConnectionOne(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection != null) {
                throw new MyException("绑定失败，该第三方账号已绑定" + userConnection.getUserName() + "系统账户");
            }
            SystemUserVo systemUser = new SystemUserVo();
            systemUser.setUsername(username);
            this.createConnection(systemUser, authUser);
        } else {
            throw new MyException("绑定失败，您无权绑定别人的账号");
        }
    }

    @Override
    public void unbind(BindUserAo bindUser, String oauthType) {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            this.userConnectionService.deleteSystemUserConnection(username, oauthType);
        } else {
            throw new MyException("解绑失败，您无权解绑别人的账号");
        }
    }

    private void createConnection(SystemUserVo systemUser, AuthUser authUser) {
        SystemUserConnectionAo userConnection = new SystemUserConnectionAo();
        userConnection.setUserName(systemUser.getUsername());
        userConnection.setProviderName(authUser.getSource().toString());
        userConnection.setProviderUserId(authUser.getUuid());
        userConnection.setProviderUserName(authUser.getUsername());
        userConnection.setImageUrl(authUser.getAvatar());
        userConnection.setNickName(authUser.getNickname());
        userConnection.setLocation(authUser.getLocation());
        this.userConnectionService.createSystemUserConnection(userConnection);
    }

    private AuthCallback resolveAuthCallback(AuthCallback callback) {
        int stateLength = 3;
        String state = callback.getState();
        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(state, StringConstant.DOUBLE_COLON);
        if (strings.length == stateLength) {
            callback.setState(strings[0] + StringConstant.DOUBLE_COLON + strings[1]);
        }
        return callback;
    }

    private AuthSource getAuthSource(String type) {
        if (StrUtil.isNotBlank(type)) {
            return AuthSource.valueOf(type.toUpperCase());
        } else {
            throw new MyException(String.format("暂不支持%s第三方登录", type));
        }
    }

    private boolean isCurrentUser(String username) {
        String currentUsername = BaseUsersUtil.getCurrentUsername();
        return StringUtils.equalsIgnoreCase(username, currentUsername);
    }

    private OAuth2AccessToken getOauth2AccessToken(SystemUserVo user) {
        final HttpServletRequest httpServletRequest = BaseUtil.getHttpServletRequest();
        httpServletRequest.setAttribute(ParamsConstant.LOGIN_TYPE, SocialConstant.SOCIAL_LOGIN);
        String socialLoginClientId = properties.getSocialLoginClientId();
        ClientDetails clientDetails = null;
        try {
            clientDetails = redisClientDetailsService.loadClientByClientId(socialLoginClientId);
        } catch (Exception e) {
            throw new MyException("获取第三方登录可用的Client失败");
        }
        if (clientDetails == null) {
            throw new MyException("未找到第三方登录可用的Client");
        }
        Map<String, String> requestParameters = new HashMap<>(5);
        requestParameters.put(ParamsConstant.GRANT_TYPE, GrantTypeConstant.PASSWORD);
        requestParameters.put(USERNAME, user.getUsername());
        requestParameters.put(PASSWORD, SocialConstant.setSocialLoginPassword());

        String grantTypes = String.join(StringConstant.COMMA, clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }
}
