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
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zclcs
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    public UserTokenVo loginByUsername(LoginByUsernameAo loginByUsernameAo) {
        LoginVo loginVoByUsername = SystemCacheUtil.getLoginVoByUsername(loginByUsernameAo.getUsername());
        Optional.ofNullable(loginVoByUsername).map(LoginVo::getUsername).filter(StrUtil::isNotBlank).orElseThrow(() -> new MyException("用户名或密码错误"));
        if (!BCrypt.checkpw(loginByUsernameAo.getPassword(), loginVoByUsername.getPassword())) {
            throw new MyException("用户名或密码错误");
        }
        if (Dict.USER_STATUS_0.equals(loginVoByUsername.getStatus())) {
            throw new MyException("用户已被锁定");
        }
        LoginHelper.login(loginVoByUsername);
        return UserTokenVo.builder()
                .token(StpUtil.getTokenValue())
                .expire(StpUtil.getTokenTimeout())
                .userinfo(loginVoByUsername)
                .build();
    }

    public UserTokenVo loginByMobile(String mobile) {
        LoginVo loginVoByUsername = SystemCacheUtil.getLoginVoByMobile(mobile);
        Optional.ofNullable(loginVoByUsername).map(LoginVo::getUsername).filter(StrUtil::isNotBlank).orElseThrow(() -> new MyException("手机号错误"));
        if (Dict.USER_STATUS_0.equals(loginVoByUsername.getStatus())) {
            throw new MyException("用户已被锁定");
        }
        LoginHelper.login(loginVoByUsername);
        return UserTokenVo.builder()
                .token(StpUtil.getTokenValue())
                .expire(StpUtil.getTokenTimeout())
                .userinfo(loginVoByUsername)
                .build();
    }

}
