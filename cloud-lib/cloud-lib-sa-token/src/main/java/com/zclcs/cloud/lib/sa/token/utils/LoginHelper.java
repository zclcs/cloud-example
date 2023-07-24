package com.zclcs.cloud.lib.sa.token.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.platform.system.api.bean.vo.LoginVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 登录鉴权助手
 * <p>
 * user_type 为 用户类型 同一个用户表 可以有多种用户类型 例如 pc,app deivce 为 设备类型 同一个用户类型 可以有 多种设备类型 例如 web,ios
 * 可以组成 用户类型与设备类型多对多的 权限灵活控制
 * <p>
 * 多用户体系 针对 多种用户类型 但权限控制不一致 可以组成 多用户类型表与多设备类型 分别控制权限
 *
 * @author zclcs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

    /**
     * 登录系统
     *
     * @param loginVo 登录用户信息
     */
    public static void login(LoginVo loginVo) {
        SaHolder.getStorage().set(RedisCachePrefix.USER_LOGIN, loginVo);
        StpUtil.login(loginVo.getUsername());
        setLoginVo(loginVo);
    }

    /**
     * 设置用户数据(多级缓存)
     */
    public static void setLoginVo(LoginVo loginVo) {
        StpUtil.getTokenSession().set(RedisCachePrefix.USER_LOGIN, loginVo);
    }

    /**
     * 获取用户(多级缓存)
     */
    public static LoginVo getLoginVo() {
        LoginVo loginVo = (LoginVo) SaHolder.getStorage().get(RedisCachePrefix.USER_LOGIN);
        if (loginVo != null) {
            return loginVo;
        }
        loginVo = (LoginVo) StpUtil.getTokenSession().get(RedisCachePrefix.USER_LOGIN);
        SaHolder.getStorage().set(RedisCachePrefix.USER_LOGIN, loginVo);
        return loginVo;
    }

    /**
     * 获取用户
     */
    public static LoginVo getLoginVo(String token) {
        return (LoginVo) StpUtil.getTokenSessionByToken(token).get(RedisCachePrefix.USER_LOGIN);
    }

    /**
     * 获取用户id
     */
    public static String getUserName() {
        LoginVo loginVo = getLoginVo();
        if (ObjectUtil.isNull(loginVo)) {
            String loginId = StpUtil.getLoginIdAsString();
            return loginId;
        }
        return loginVo.getUsername();
    }

}
