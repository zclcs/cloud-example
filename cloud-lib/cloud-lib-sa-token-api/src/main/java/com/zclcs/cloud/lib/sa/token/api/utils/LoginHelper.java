package com.zclcs.cloud.lib.sa.token.api.utils;

import cn.dev33.satoken.stp.StpUtil;
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
        StpUtil.login(loginVo.getUsername());
    }

    /**
     * 获取用户id
     */
    public static String getUsername() {
        return StpUtil.getLoginIdAsString();
    }

    /**
     * 获取用户id
     */
    public static String getUsernameWithNull() {
        try {
            return StpUtil.getLoginIdAsString();
        } catch (Exception e) {
            return null;
        }
    }

}
