package com.zclcs.cloud.lib.sa.token.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.platform.system.api.bean.vo.UserVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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

    public static final String JOIN_CODE = ":";

    public static final String LOGIN_USER_KEY = "userVo";

    /**
     * 登录系统
     *
     * @param userVo 登录用户信息
     */
    public static void login(UserVo userVo) {
        SaHolder.getStorage().set(LOGIN_USER_KEY, userVo);
        StpUtil.login(userVo.getUserId());
        setUserVo(userVo);
    }

    /**
     * 设置用户数据(多级缓存)
     */
    public static void setUserVo(UserVo userVo) {
        StpUtil.getTokenSession().set(LOGIN_USER_KEY, userVo);
    }

    /**
     * 获取用户(多级缓存)
     */
    public static UserVo getUserVo() {
        UserVo userVo = (UserVo) SaHolder.getStorage().get(LOGIN_USER_KEY);
        if (userVo != null) {
            return userVo;
        }
        userVo = (UserVo) StpUtil.getTokenSession().get(LOGIN_USER_KEY);
        SaHolder.getStorage().set(LOGIN_USER_KEY, userVo);
        return userVo;
    }

    /**
     * 获取用户
     */
    public static UserVo getUserVo(String token) {
        return (UserVo) StpUtil.getTokenSessionByToken(token).get(LOGIN_USER_KEY);
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        UserVo userVo = getUserVo();
        if (ObjectUtil.isNull(userVo)) {
            String loginId = StpUtil.getLoginIdAsString();
            String[] strs = StringUtils.split(loginId, JOIN_CODE);
            // 用户id在总是在最后
            assert strs != null;
            String userId = strs[strs.length - 1];
            if (StrUtil.isBlank(userId)) {
                throw new RuntimeException("登录用户: LoginId异常 => " + loginId);
            }
            return Long.parseLong(userId);
        }
        return userVo.getUserId();
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        return getUserVo().getDeptId();
    }

    /**
     * 获取用户账户
     */
    public static String getUsername() {
        return getUserVo().getUsername();
    }

    public static String getUsername(String token) {
        return getUserVo(token).getUsername();
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return 1L == userId;
    }

    public static boolean isAdmin() {
        return isAdmin(getUserId());
    }

}
