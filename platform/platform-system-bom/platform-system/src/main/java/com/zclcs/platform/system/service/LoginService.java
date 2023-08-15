package com.zclcs.platform.system.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.sa.token.api.utils.LoginHelper;
import com.zclcs.platform.system.api.bean.ao.LoginByUsernameAo;
import com.zclcs.platform.system.api.bean.vo.LoginVo;
import com.zclcs.platform.system.api.bean.vo.UserTokenVo;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zclcs
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    public UserTokenVo loginByUsername(LoginByUsernameAo loginByUsernameAo) {
        String username = loginByUsernameAo.getUsername();
        LoginVo loginVo = SystemCacheUtil.getLoginVoByUsername(username);
        Optional.ofNullable(loginVo).map(LoginVo::getUsername).filter(StrUtil::isNotBlank).orElseThrow(() -> new MyException("用户名或密码错误"));
        if (!BCrypt.checkpw(loginByUsernameAo.getPassword(), loginVo.getPassword())) {
            throw new MyException("用户名或密码错误");
        }
        checkUserStatus(loginVo);
        return login(loginVo);
    }

    public UserTokenVo loginByMobile(String mobile) {
        LoginVo loginVo = SystemCacheUtil.getLoginVoByMobile(mobile);
        Optional.ofNullable(loginVo).map(LoginVo::getUsername).filter(StrUtil::isNotBlank).orElseThrow(() -> new MyException("手机号错误"));
        checkUserStatus(loginVo);
        return login(loginVo);
    }

    public void logout() {
        try {
            StpUtil.logout();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void checkUserStatus(LoginVo loginVo) {
        if (Dict.USER_STATUS_0.equals(loginVo.getStatus())) {
            throw new MyException("用户已被锁定");
        }
    }

    private UserTokenVo login(LoginVo loginVo) {
        LoginHelper.login(loginVo);
        String accessToken = StpUtil.getTokenValue();
        return UserTokenVo.builder()
                .token(accessToken)
                .expire(StpUtil.getTokenTimeout())
                .userinfo(loginVo)
                .build();
    }

}
