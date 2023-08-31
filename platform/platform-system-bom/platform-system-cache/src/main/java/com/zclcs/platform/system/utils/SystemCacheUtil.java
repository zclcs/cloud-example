package com.zclcs.platform.system.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.platform.system.api.bean.cache.*;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.LoginVo;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.cache.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zclcs
 */
@Slf4j
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
    private static UserPermissionsCache USER_PERMISSIONS_CACHE;
    private static UserRoutersCache USER_ROUTERS_CACHE;
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
    private final UserPermissionsCache userPermissionsCache;
    private final UserRoutersCache userRoutersCache;

    public static OauthClientDetailsCacheVo getOauthClientDetailsCache(String clientId) {
        return OAUTH_CLIENT_DETAILS_CACHE.findCache(clientId);
    }

    public static void deleteOauthClientDetailsCache(String clientId) {
        OAUTH_CLIENT_DETAILS_CACHE.deleteCache(clientId);
    }

    public static void deleteOauthClientDetailsCache(Object... clientId) {
        OAUTH_CLIENT_DETAILS_CACHE.deleteCache(clientId);
    }

    public static UserCacheVo getUserCache(String username) {
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

    public static RoleCacheVo getRoleByRoleId(Long roleId) {
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

    public static List<String> getPermissionsByUsername(String username) {
        return USER_PERMISSIONS_CACHE.findCache(username);
    }

    public static void deletePermissionsByUsername(String username) {
        USER_PERMISSIONS_CACHE.deleteCache(username);
    }

    public static void deletePermissionsByUsernames(Object... usernames) {
        USER_PERMISSIONS_CACHE.deleteCache(usernames);
    }

    public static List<VueRouter<MenuVo>> getRoutersByUsername(String username) {
        return USER_ROUTERS_CACHE.findCache(username);
    }

    public static void deleteRoutersByUsername(String username) {
        USER_ROUTERS_CACHE.deleteCache(username);
    }

    public static void deleteRoutersByUsernames(Object... usernames) {
        USER_ROUTERS_CACHE.deleteCache(usernames);
    }

    public static MenuCacheVo getMenuByMenuId(Long menuId) {
        return MENU_CACHE.findCache(menuId);
    }

    public static List<MenuCacheVo> getMenusByMenuIds(List<Long> menuIds) {
        return MENU_CACHE.getMenusByMenuIds(menuIds).stream().filter(Objects::nonNull).toList();
    }

    public static void deleteMenuByMenuId(Long menuId) {
        MENU_CACHE.deleteCache(menuId);
    }

    public static void deleteMenuByMenuIds(Object... menuIds) {
        MENU_CACHE.deleteCache(menuIds);
    }

    public static DeptCacheVo getDeptByDeptId(Long deptId) {
        return DEPT_CACHE.findCache(deptId);
    }

    public static void deleteDeptByDeptId(Long deptId) {
        DEPT_CACHE.deleteCache(deptId);
    }

    public static void deleteDeptByDeptIds(Object... deptIds) {
        DEPT_CACHE.deleteCache(deptIds);
    }

    public static List<RoleCacheVo> getRolesByUserId(Long userId) {
        List<RoleCacheVo> roles = new ArrayList<>();
        List<Long> roleIds = getRoleIdsByUserId(userId);
        if (CollectionUtil.isNotEmpty(roleIds)) {
            for (Long roleId : roleIds) {
                roles.add(SystemCacheUtil.getRoleByRoleId(roleId));
            }
        }
        return roles;
    }

    public static List<String> getRoleCodesByUsername(String username) {
        UserCacheVo userCache = SystemCacheUtil.getUserCache(username);
        List<RoleCacheVo> roles = SystemCacheUtil.getRolesByUserId(userCache.getUserId());
        return roles.stream().map(RoleCacheVo::getRoleCode).toList();
    }

    public static LoginVo getLoginVoByUsername(String username) {
        UserCacheVo userCache = getUserCache(username);
        if (userCache == null || StrUtil.isBlank(userCache.getUsername())) {
            return null;
        }
        LoginVo loginVo = new LoginVo();
        BeanUtil.copyProperties(userCache, loginVo);
        return loginVo;
    }

    public static LoginVo getLoginVoByMobile(String mobile) {
        String username = getUsernameByMobile(mobile);
        if (StrUtil.isBlank(username)) {
            return null;
        }
        return getLoginVoByUsername(username);
    }

    public static String getDeptIdStringByUsername(String username) {
        UserCacheVo userCacheVo = getUserCache(username);
        List<Long> deptIdsByUserId = getDeptIdsByUserId(userCacheVo.getUserId());
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
        USER_PERMISSIONS_CACHE = userPermissionsCache;
        USER_ROUTERS_CACHE = userRoutersCache;
    }
}
