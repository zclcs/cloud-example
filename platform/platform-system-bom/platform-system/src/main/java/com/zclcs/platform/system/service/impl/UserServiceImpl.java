package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.properties.GlobalProperties;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.cloud.lib.security.utils.PasswordUtil;
import com.zclcs.cloud.lib.security.utils.SecurityUtil;
import com.zclcs.platform.system.api.bean.ao.UserAo;
import com.zclcs.platform.system.api.bean.cache.MenuCacheBean;
import com.zclcs.platform.system.api.bean.cache.RoleCacheBean;
import com.zclcs.platform.system.api.bean.entity.User;
import com.zclcs.platform.system.api.bean.entity.UserDataPermission;
import com.zclcs.platform.system.api.bean.entity.UserRole;
import com.zclcs.platform.system.api.bean.router.RouterMeta;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.api.bean.vo.UserVo;
import com.zclcs.platform.system.api.utils.BaseRouterUtil;
import com.zclcs.platform.system.mapper.UserMapper;
import com.zclcs.platform.system.service.DeptService;
import com.zclcs.platform.system.service.UserDataPermissionService;
import com.zclcs.platform.system.service.UserRoleService;
import com.zclcs.platform.system.service.UserService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 用户 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserRoleService userRoleService;
    private final UserDataPermissionService userDataPermissionService;
    private final DeptService deptService;
    private final GlobalProperties globalProperties;

    @Override
    public BasePage<UserVo> findUserPage(BasePageAo basePageAo, UserVo userVo) {
        BasePage<UserVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        BasePage<UserVo> pageVo = this.baseMapper.findPageVo(basePage, queryWrapper);
        pageVo.getList().forEach(vo -> {
            Long userId = vo.getUserId();
            List<RoleCacheBean> roles = SystemCacheUtil.getRolesByUserId(userId);
            if (CollectionUtil.isNotEmpty(roles)) {
                vo.setRoleIds(roles.stream().map(RoleCacheBean::getRoleId).toList());
                List<String> roleNames = roles.stream().map(RoleCacheBean::getRoleName).toList();
                vo.setRoleNames(roleNames);
                vo.setRoleNameString(String.join(StrUtil.COMMA, roleNames));
            }
            vo.setDeptIds(SystemCacheUtil.getDeptIdsByUserId(userId));
        });
        return pageVo;
    }

    @Override
    public List<UserVo> findUserList(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public UserVo findUser(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countUser(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    @Override
    public UserVo findByName(String username) {
        QueryWrapper<UserVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.eqNotBlank(queryWrapper, "tb.username", username);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public UserVo findByMobile(String mobile) {
        QueryWrapper<UserVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.eqNotBlank(queryWrapper, "tb.mobile", mobile);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    private QueryWrapper<UserVo> getQueryWrapper(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = new QueryWrapper<>();
        AtomicReference<List<Long>> deptList = new AtomicReference<>();
        Optional.ofNullable(userVo.getDeptId()).ifPresent(deptId -> {
            List<Long> childDeptId = deptService.getChildDeptId(deptId);
            if (CollectionUtil.isNotEmpty(childDeptId)) {
                deptList.set(childDeptId);
            } else {
                deptList.set(CollectionUtil.newArrayList(CommonCore.TOP_PARENT_ID));
            }
        });
        QueryWrapperUtil.inNotEmpty(queryWrapper, "tb.dept_id", deptList.get());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "tb.username", userVo.getUsername());
        return queryWrapper;
    }

    @Override
    public List<VueRouter<MenuVo>> findUserRouters(String username) {
        List<VueRouter<MenuVo>> routes = new ArrayList<>();
        List<MenuCacheBean> menus = SystemCacheUtil.getMenusByUsername(username);
        List<MenuCacheBean> userMenus = menus.stream().filter(Objects::nonNull)
                .filter(menu -> !menu.getType().equals(Dict.MENU_TYPE_1))
                .sorted(Comparator.comparing(MenuCacheBean::getOrderNum)).toList();
        userMenus.forEach(menu -> {
            VueRouter<MenuVo> route = new VueRouter<>();
            route.setId(menu.getMenuId());
            route.setCode(menu.getMenuCode());
            route.setParentCode(menu.getParentCode());
            route.setPath(menu.getPath());
            route.setName(StrUtil.isNotBlank(menu.getKeepAliveName()) ? menu.getKeepAliveName() : menu.getMenuName());
            route.setComponent(menu.getComponent());
            route.setRedirect(menu.getRedirect());
            route.setMeta(new RouterMeta(
                    menu.getMenuName(),
                    menu.getIcon(),
                    Dict.YES_NO_1.equals(menu.getHideMenu()),
                    Dict.YES_NO_1.equals(menu.getIgnoreKeepAlive()),
                    Dict.YES_NO_1.equals(menu.getHideBreadcrumb()),
                    Dict.YES_NO_1.equals(menu.getHideChildrenInMenu()),
                    menu.getCurrentActiveMenu()));
            routes.add(route);
        });
        return BaseRouterUtil.buildVueRouter(routes);
    }

    @Override
    public List<String> findUserPermissions(String username) {
        return SystemCacheUtil.getPermissionsByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(UserAo userAo) {
        validateUsername(userAo.getUsername(), userAo.getUserId());
        validateMobile(userAo.getMobile(), userAo.getUserId());
        deleteCache(userAo);
        User user = new User();
        BeanUtil.copyProperties(userAo, user);
        user.setAvatar(CommonCore.DEFAULT_AVATAR);
        user.setPassword(PasswordUtil.PASSWORD_ENCODER.encode(globalProperties.getDefaultPassword()));
        this.save(user);
        Long userId = userAo.getUserId();
        SystemCacheUtil.deleteRoleIdsByUserId(userId);
        List<UserRole> userRoles = getUserRoles(user, userAo.getRoleIds());
        userRoleService.saveBatch(userRoles);
        SystemCacheUtil.deleteDeptIdsByUserId(userId);
        List<UserDataPermission> userDataPermissions = getUserDataPermissions(user, userAo.getDeptIds());
        userDataPermissionService.saveBatch(userDataPermissions);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(UserAo userAo) {
        validateUsername(userAo.getUsername(), userAo.getUserId());
        validateMobile(userAo.getMobile(), userAo.getUserId());
        deleteCache(userAo);
        User user = new User();
        BeanUtil.copyProperties(userAo, user);
        Long userId = user.getUserId();
        // 更新用户
        user.setPassword(null);

        this.updateById(user);

        userRoleService.lambdaUpdate().eq(UserRole::getUserId, userId).remove();
        List<UserRole> userRoles = getUserRoles(user, userAo.getRoleIds());
        userRoleService.saveBatch(userRoles);
        SystemCacheUtil.deleteRoleIdsByUserId(userId);

        userDataPermissionService.lambdaUpdate().eq(UserDataPermission::getUserId, userId).remove();
        List<UserDataPermission> userDataPermissions = getUserDataPermissions(user, userAo.getDeptIds());
        userDataPermissionService.saveBatch(userDataPermissions);
        SystemCacheUtil.deleteDeptIdsByUserId(userId);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(List<Long> ids) {
        List<User> list = this.lambdaQuery().in(User::getUserId, ids).list();
        this.removeByIds(ids);
        Object[] usernames = list.stream().map(User::getUsername).toList().toArray();
        Object[] mobiles = list.stream().map(User::getMobile).filter(StrUtil::isNotBlank).toList().toArray();
        Object[] userIds = ids.toArray();
        SystemCacheUtil.deleteUserCaches(usernames);
        SystemCacheUtil.deleteUsernameByMobiles(mobiles);
        // 删除用户角色
        userRoleService.lambdaUpdate().in(UserRole::getUserId, ids).remove();
        SystemCacheUtil.deleteRoleIdsByUserIds(userIds);
        // 删除用户权限
        userDataPermissionService.lambdaUpdate().in(UserDataPermission::getUserId, ids).remove();
        SystemCacheUtil.deleteDeptIdsByUserIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginTime(String username) {
        this.lambdaUpdate().eq(User::getUsername, username).set(User::getLastLoginTime, DateUtil.date()).update();
        SystemCacheUtil.deleteUserCache(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String username, String password) {
        String currentUsername = SecurityUtil.getUsername();
        this.lambdaUpdate().eq(User::getUsername, Optional.ofNullable(username).filter(StrUtil::isNotBlank).orElse(currentUsername))
                .set(User::getPassword, PasswordUtil.PASSWORD_ENCODER.encode(password)).update();
        SystemCacheUtil.deleteUserCache(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String username, String status) {
        String orElse = Optional.ofNullable(status).filter(StrUtil::isNotBlank).orElse(Security.STATUS_LOCK);
        String currentUsername = SecurityUtil.getUsername();
        this.lambdaUpdate().eq(User::getUsername, Optional.ofNullable(username).filter(StrUtil::isNotBlank).orElse(currentUsername))
                .set(User::getStatus, orElse)
                .update();
        SystemCacheUtil.deleteUserCache(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(List<String> usernames) {
        this.lambdaUpdate().in(User::getUsername, usernames)
                .set(User::getPassword, PasswordUtil.PASSWORD_ENCODER.encode(globalProperties.getDefaultPassword())).update();
        SystemCacheUtil.deleteUserCaches(usernames.toArray());
    }

    private List<UserRole> getUserRoles(User user, List<Long> roleIds) {
        List<UserRole> userRoles = new ArrayList<>();
        roleIds.stream().filter(Objects::nonNull).forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        });
        return userRoles;
    }

    private List<UserDataPermission> getUserDataPermissions(User user, List<Long> deptIds) {
        List<UserDataPermission> userDataPermissions = new ArrayList<>();
        deptIds.stream().filter(Objects::nonNull).forEach(deptId -> {
            UserDataPermission permission = new UserDataPermission();
            permission.setUserId(user.getUserId());
            permission.setDeptId(deptId);
            userDataPermissions.add(permission);
        });
        return userDataPermissions;
    }

    @Override
    public void validateUsername(String username, Long userId) {
        UserVo userVo = this.findByName(username);
        if (userVo != null && !userVo.getUserId().equals(userId)) {
            throw new MyException("用户名重复");
        }
    }

    @Override
    public void validateMobile(String mobile, Long userId) {
        if (StrUtil.isNotBlank(mobile)) {
            UserVo userVo = this.findByMobile(mobile);
            if (userVo != null && !userVo.getUserId().equals(userId)) {
                throw new MyException("手机号重复");
            }
        }
    }

    private void deleteCache(UserAo userAo) {
        deleteUserCache(userAo.getUsername(), userAo.getMobile());
        if (userAo.getUserId() != null) {
            User one = this.lambdaQuery().eq(User::getUserId, userAo.getUserId()).one();
            deleteUserCache(one.getUsername(), one.getMobile());
        }
    }

    private void deleteUserCache(String username, String mobile) {
        SystemCacheUtil.deleteUserCache(username);
        if (StrUtil.isNotBlank(mobile)) {
            SystemCacheUtil.deleteUsernameByMobile(mobile);
        }
    }

}
