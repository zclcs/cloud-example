package com.zclcs.platform.system.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.platform.system.api.bean.cache.*;
import com.zclcs.platform.system.api.bean.vo.LoginVo;
import com.zclcs.platform.system.api.bean.vo.UserVo;
import com.zclcs.platform.system.cache.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zclcs
 */
@Component
@RequiredArgsConstructor
public class SystemCacheUtil {

    private static OauthClientDetailsCache OAUTH_CLIENT_DETAILS_CACHE;
    private static UserCache USER_CACHE;
    private static UserMobileCache USER_MOBILE_CACHE;
    private static UserRoleCache USER_ROLE_CACHE;
    private static UserDataPermissionCache USER_DATA_PERMISSION_CACHE;
    private static RoleCache ROLE_CACHE;
    private static RoleMenuCache ROLE_MENU_CACHE;
    private static MenuCache MENU_CACHE;
    private static DeptCache DEPT_CACHE;
    private final OauthClientDetailsCache oauthClientDetailsCache;
    private final UserCache userCache;
    private final UserMobileCache userMobileCache;
    private final UserRoleCache userRoleCache;
    private final UserDataPermissionCache userDataPermissionCache;
    private final RoleCache roleCache;
    private final RoleMenuCache roleMenuCache;
    private final MenuCache menuCache;
    private final DeptCache deptCache;

    public static OauthClientDetailsCacheBean getOauthClientDetailsCache(String clientId) {
        return OAUTH_CLIENT_DETAILS_CACHE.findCache(clientId);
    }

    public static void deleteOauthClientDetailsCache(String clientId) {
        OAUTH_CLIENT_DETAILS_CACHE.deleteCache(clientId);
    }

    public static void deleteOauthClientDetailsCache(Object... clientId) {
        OAUTH_CLIENT_DETAILS_CACHE.deleteCache(clientId);
    }

    public static UserCacheBean getUserCache(String username) {
        return USER_CACHE.findCache(username);
    }

    public static void deleteUserCache(String username) {
        USER_CACHE.deleteCache(username);
    }

    public static void deleteUserCaches(Object... usernames) {
        USER_CACHE.deleteCache(usernames);
    }

    public static String getUsernameByMobile(String mobile) {
        return USER_MOBILE_CACHE.findCache(mobile);
    }

    public static void deleteUsernameByMobile(String mobile) {
        USER_MOBILE_CACHE.deleteCache(mobile);
    }

    public static void deleteUsernameByMobiles(Object... mobiles) {
        USER_MOBILE_CACHE.deleteCache(mobiles);
    }

    public static List<Long> getRoleIdsByUserId(Long userId) {
        return USER_ROLE_CACHE.findCache(userId);
    }

    public static void deleteRoleIdsByUserId(Long userId) {
        USER_ROLE_CACHE.deleteCache(userId);
    }

    public static void deleteRoleIdsByUserIds(Object... userIds) {
        USER_ROLE_CACHE.deleteCache(userIds);
    }

    public static List<Long> getDeptIdsByUserId(Long userId) {
        return USER_DATA_PERMISSION_CACHE.findCache(userId);
    }

    public static void deleteDeptIdsByUserId(Long userId) {
        USER_DATA_PERMISSION_CACHE.deleteCache(userId);
    }

    public static void deleteDeptIdsByUserIds(Object... userIds) {
        USER_DATA_PERMISSION_CACHE.deleteCache(userIds);
    }

    public static RoleCacheBean getRoleByRoleId(Long roleId) {
        return ROLE_CACHE.findCache(roleId);
    }

    public static void deleteRoleByRoleId(Long roleId) {
        ROLE_CACHE.deleteCache(roleId);
    }

    public static void deleteRoleByRoleIds(Object... roleIds) {
        ROLE_CACHE.deleteCache(roleIds);
    }

    public static List<Long> getMenuIdsByRoleId(Long roleId) {
        return ROLE_MENU_CACHE.findCache(roleId);
    }

    public static void deleteMenuIdsByRoleId(Long roleId) {
        ROLE_MENU_CACHE.deleteCache(roleId);
    }

    public static void deleteMenuIdsByRoleIds(Object... roleIds) {
        ROLE_MENU_CACHE.deleteCache(roleIds);
    }

    public static MenuCacheBean getMenuByMenuId(Long menuId) {
        return MENU_CACHE.findCache(menuId);
    }

    public static List<MenuCacheBean> getMenusByMenuIds(List<Long> menuIds) {
        return MENU_CACHE.getMenusByMenuIds(menuIds).stream().filter(Objects::nonNull).toList();
    }

    public static void deleteMenuByMenuId(Long menuId) {
        MENU_CACHE.deleteCache(menuId);
    }

    public static void deleteMenuByMenuIds(Object... menuIds) {
        MENU_CACHE.deleteCache(menuIds);
    }

    public static DeptCacheBean getDeptByDeptId(Long deptId) {
        return DEPT_CACHE.findCache(deptId);
    }

    public static void deleteDeptByDeptId(Long deptId) {
        DEPT_CACHE.deleteCache(deptId);
    }

    public static void deleteDeptByDeptIds(Object... deptIds) {
        DEPT_CACHE.deleteCache(deptIds);
    }

    public static List<RoleCacheBean> getRolesByUserId(Long userId) {
        List<RoleCacheBean> roles = new ArrayList<>();
        List<Long> roleIds = getRoleIdsByUserId(userId);
        if (CollectionUtil.isNotEmpty(roleIds)) {
            for (Long roleId : roleIds) {
                roles.add(SystemCacheUtil.getRoleByRoleId(roleId));
            }
        }
        return roles;
    }

    public static List<String> getRoleCodesByUsername(String username) {
        UserCacheBean userCache = SystemCacheUtil.getUserCache(username);
        List<RoleCacheBean> roles = SystemCacheUtil.getRolesByUserId(userCache.getUserId());
        return roles.stream().map(RoleCacheBean::getRoleCode).toList();
    }

    public static List<MenuCacheBean> getMenusByRoleIds(List<Long> roleIds) {
        Set<Long> menuIds = new HashSet<>();
        for (Long roleId : roleIds) {
            menuIds.addAll(SystemCacheUtil.getMenuIdsByRoleId(roleId));
        }
        return getMenusByMenuIds(menuIds.stream().toList());
    }

    public static List<MenuCacheBean> getMenusByUsername(String username) {
        UserCacheBean userCache = SystemCacheUtil.getUserCache(username);
        List<RoleCacheBean> roles = SystemCacheUtil.getRolesByUserId(userCache.getUserId());
        return SystemCacheUtil.getMenusByRoleIds(roles.stream().map(RoleCacheBean::getRoleId).toList());
    }

    public static List<String> getPermissionsByUsername(String username) {
        return SystemCacheUtil.getMenusByUsername(username).stream().filter(Objects::nonNull).map(MenuCacheBean::getPerms)
                .filter(StrUtil::isNotBlank).toList();
    }

    public static UserVo getUserByUsername(String username) {
        UserCacheBean userCache = getUserCache(username);
        if (userCache == null || StrUtil.isBlank(userCache.getUsername())) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(userCache, userVo);
        Long userId = userVo.getUserId();
        List<RoleCacheBean> roles = getRolesByUserId(userId);
        if (CollectionUtil.isNotEmpty(roles)) {
            List<MenuCacheBean> menus = getMenusByRoleIds(roles.stream().map(RoleCacheBean::getRoleId).toList());
            userVo.setRoleIds(roles.stream().map(RoleCacheBean::getRoleId).toList());
            userVo.setRoleNames(roles.stream().map(RoleCacheBean::getRoleName).toList());
            List<String> permissions = menus.stream().filter(Objects::nonNull).map(MenuCacheBean::getPerms)
                    .filter(StrUtil::isNotBlank).toList();
            userVo.setPermissions(permissions);
        }
        userVo.setDeptIds(getDeptIdsByUserId(userId));
        return userVo;
    }

    public static LoginVo getLoginVoByUsername(String username) {
        UserCacheBean userCache = getUserCache(username);
        if (userCache == null || StrUtil.isBlank(userCache.getUsername())) {
            return null;
        }
        LoginVo loginVo = new LoginVo();
        BeanUtil.copyProperties(userCache, loginVo);
        return loginVo;
    }

    public static UserVo getUserByMobile(String mobile) {
        String username = getUsernameByMobile(mobile);
        if (StrUtil.isBlank(username)) {
            return null;
        }
        return getUserByUsername(username);
    }

    public static LoginVo getLoginVoByMobile(String mobile) {
        String username = getUsernameByMobile(mobile);
        if (StrUtil.isBlank(username)) {
            return null;
        }
        return getLoginVoByUsername(username);
    }

    public static String getDeptIdStringByUsername(String username) {
        UserCacheBean userCacheBean = getUserCache(username);
        List<Long> deptIdsByUserId = getDeptIdsByUserId(userCacheBean.getUserId());
        String deptIdString = "";
        if (CollectionUtil.isNotEmpty(deptIdsByUserId)) {
            deptIdString = deptIdsByUserId.stream().map(String::valueOf).collect(Collectors.joining(Strings.COMMA));
        }
        return deptIdString;
    }

    @PostConstruct
    public void init() {
        OAUTH_CLIENT_DETAILS_CACHE = oauthClientDetailsCache;
        USER_CACHE = userCache;
        USER_MOBILE_CACHE = userMobileCache;
        USER_ROLE_CACHE = userRoleCache;
        USER_DATA_PERMISSION_CACHE = userDataPermissionCache;
        ROLE_CACHE = roleCache;
        ROLE_MENU_CACHE = roleMenuCache;
        MENU_CACHE = menuCache;
        DEPT_CACHE = deptCache;
    }
}
