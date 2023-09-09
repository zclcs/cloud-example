package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.core.properties.GlobalProperties;
import com.zclcs.cloud.lib.sa.token.api.utils.LoginHelper;
import com.zclcs.common.export.excel.starter.listener.SimpleExportListener;
import com.zclcs.common.export.excel.starter.service.ExportExcelService;
import com.zclcs.common.web.starter.utils.WebUtil;
import com.zclcs.platform.system.api.bean.ao.UserAo;
import com.zclcs.platform.system.api.bean.cache.RoleCacheVo;
import com.zclcs.platform.system.api.bean.entity.User;
import com.zclcs.platform.system.api.bean.entity.UserDataPermission;
import com.zclcs.platform.system.api.bean.entity.UserRole;
import com.zclcs.platform.system.api.bean.excel.UserExcelVo;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.api.bean.vo.UserVo;
import com.zclcs.platform.system.mapper.UserMapper;
import com.zclcs.platform.system.service.DeptService;
import com.zclcs.platform.system.service.UserDataPermissionService;
import com.zclcs.platform.system.service.UserRoleService;
import com.zclcs.platform.system.service.UserService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.zclcs.platform.system.api.bean.entity.table.UserDataPermissionTableDef.USER_DATA_PERMISSION;
import static com.zclcs.platform.system.api.bean.entity.table.UserRoleTableDef.USER_ROLE;
import static com.zclcs.platform.system.api.bean.entity.table.UserTableDef.USER;

/**
 * 用户 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:34.182
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserRoleService userRoleService;
    private final UserDataPermissionService userDataPermissionService;
    private final DeptService deptService;
    private final GlobalProperties globalProperties;

    @Override
    public BasePage<UserVo> findUserPage(BasePageAo basePageAo, UserVo userVo) {
        QueryWrapper queryWrapper = getQueryWrapper(userVo);
        Page<UserVo> pageVo = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, UserVo.class);
        pageVo.getRecords().forEach(this::setUserVo);
        return new BasePage<>(pageVo);
    }

    @Override
    public List<UserVo> findUserList(UserVo userVo) {
        QueryWrapper queryWrapper = getQueryWrapper(userVo);
        return this.mapper.selectListByQueryAs(queryWrapper, UserVo.class);
    }

    @Override
    public UserVo findUser(UserVo userVo) {
        QueryWrapper queryWrapper = getQueryWrapper(userVo);
        UserVo one = this.mapper.selectOneByQueryAs(queryWrapper, UserVo.class);
        setUserVo(one);
        return one;
    }

    @Override
    public Long countUser(UserVo userVo) {
        QueryWrapper queryWrapper = getQueryWrapper(userVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    @Override
    public UserVo findByName(String username) {
        return this.queryChain().where(USER.USERNAME.eq(username)).oneAs(UserVo.class);
    }

    @Override
    public UserVo findByMobile(String mobile) {
        return this.queryChain().where(USER.MOBILE.eq(mobile)).oneAs(UserVo.class);
    }

    private QueryWrapper getQueryWrapper(UserVo userVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        AtomicReference<List<Long>> deptList = new AtomicReference<>();
        Optional.ofNullable(userVo.getDeptId()).ifPresent(deptId -> {
            List<Long> childDeptId = deptService.getChildDeptId(deptId);
            if (CollectionUtil.isNotEmpty(childDeptId)) {
                deptList.set(childDeptId);
            } else {
                deptList.set(CollectionUtil.newArrayList(CommonCore.TOP_PARENT_ID));
            }
        });
        queryWrapper.select(
                        USER.USER_ID,
                        USER.USERNAME,
                        USER.REAL_NAME,
                        USER.PASSWORD,
                        USER.DEPT_ID,
                        USER.EMAIL,
                        USER.MOBILE,
                        USER.STATUS,
                        USER.LAST_LOGIN_TIME,
                        USER.GENDER,
                        USER.IS_TAB,
                        USER.THEME,
                        USER.AVATAR,
                        USER.DESCRIPTION,
                        USER.CREATE_AT
                )
                .where(USER.USER_ID.in(deptList.get(), If::isNotEmpty))
                .and(USER.USER_ID.eq(userVo.getUserId(), If::notNull))
                .and(USER.USERNAME.like(userVo.getUsername(), If::hasText))
        ;
        return queryWrapper;
    }

    @Override
    public List<VueRouter<MenuVo>> findUserRouters(String username) {
        return SystemCacheUtil.getRoutersByUsername(username);
    }

    @Override
    public List<String> findUserPermissions(String username) {
        List<String> permissionsByUsername = SystemCacheUtil.getPermissionsByUsername(username);
        return permissionsByUsername;
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
        user.setPassword(BCrypt.hashpw(globalProperties.getDefaultPassword()));
        this.save(user);
        Long userId = user.getUserId();
        String username = user.getUsername();
        SystemCacheUtil.deletePermissionsByUsername(username);
        SystemCacheUtil.deleteRoutersByUsername(username);
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
        String username = user.getUsername();
        // 更新用户
        user.setPassword(null);

        this.updateById(user);

        SystemCacheUtil.deletePermissionsByUsername(username);
        SystemCacheUtil.deleteRoutersByUsername(username);

        userRoleService.updateChain().where(USER_ROLE.USER_ID.eq(userId)).remove();
        List<UserRole> userRoles = getUserRoles(user, userAo.getRoleIds());
        userRoleService.saveBatch(userRoles);
        SystemCacheUtil.deleteRoleIdsByUserId(userId);

        userDataPermissionService.updateChain().where(USER_DATA_PERMISSION.USER_ID.eq(userId)).remove();
        List<UserDataPermission> userDataPermissions = getUserDataPermissions(user, userAo.getDeptIds());
        userDataPermissionService.saveBatch(userDataPermissions);
        SystemCacheUtil.deleteDeptIdsByUserId(userId);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(List<Long> ids) {
        List<User> list = this.queryChain().where(USER.USER_ID.in(ids)).list();
        this.removeByIds(ids);
        Object[] usernames = list.stream().map(User::getUsername).toList().toArray();
        Object[] mobiles = list.stream().map(User::getMobile).filter(StrUtil::isNotBlank).toList().toArray();
        Object[] userIds = ids.toArray();

        SystemCacheUtil.deleteRoutersByUsernames(usernames);
        SystemCacheUtil.deleteRoutersByUsernames(usernames);

        SystemCacheUtil.deleteUserCaches(usernames);
        SystemCacheUtil.deleteUsernameByMobiles(mobiles);
        // 删除用户角色
        userRoleService.updateChain().where(USER_ROLE.USER_ID.in(ids)).remove();
        SystemCacheUtil.deleteRoleIdsByUserIds(userIds);
        // 删除用户权限
        userDataPermissionService.updateChain().where(USER_DATA_PERMISSION.USER_ID.in(ids)).remove();
        SystemCacheUtil.deleteDeptIdsByUserIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginTime(String username) {
        this.updateChain().where(USER.USERNAME.eq(username)).set(User::getLastLoginTime, DateUtil.date()).update();
        SystemCacheUtil.deleteUserCache(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String username, String password) {
        String currentUsername = LoginHelper.getUsername();
        this.updateChain().where(USER.USERNAME.eq(Optional.ofNullable(username).filter(StrUtil::isNotBlank).orElse(currentUsername)))
                .set(User::getPassword, BCrypt.hashpw(password)).update();
        SystemCacheUtil.deleteUserCache(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String username, String status) {
        String orElse = Optional.ofNullable(status).filter(StrUtil::isNotBlank).orElse(Security.STATUS_LOCK);
        String currentUsername = LoginHelper.getUsername();
        this.updateChain().where(USER.USERNAME.eq(Optional.ofNullable(username).filter(StrUtil::isNotBlank).orElse(currentUsername)))
                .set(User::getStatus, orElse)
                .update();
        SystemCacheUtil.deleteUserCache(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(List<String> usernames) {
        this.updateChain().where(USER.USERNAME.in(usernames))
                .set(User::getPassword, BCrypt.hashpw(globalProperties.getDefaultPassword())).update();
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
            throw new FieldException("用户名重复");
        }
    }

    @Override
    public void validateMobile(String mobile, Long userId) {
        if (StrUtil.isNotBlank(mobile)) {
            UserVo userVo = this.findByMobile(mobile);
            if (userVo != null && !userVo.getUserId().equals(userId)) {
                throw new FieldException("手机号重复");
            }
        }
    }

    @Override
    public void exportExcel(UserVo userVo) {
        SimpleExportListener<UserVo, UserExcelVo> routeLogVoRouteLogExcelVoSimpleExportListener = new SimpleExportListener<>(new ExportExcelService<>() {
            @Override
            public Long count(UserVo userVo) {
                return countUser(userVo);
            }

            @Override
            public List<UserExcelVo> getDataPaginateAs(UserVo userVo, Long pageNum, Long pageSize, Long totalRows) {
                QueryWrapper queryWrapper = getQueryWrapper(userVo);
                Page<UserExcelVo> pageVo = mapper.paginateAs(pageNum, pageSize, totalRows, queryWrapper, UserExcelVo.class);
                return pageVo.getRecords();
            }
        }, UserExcelVo.class.getDeclaredFields());
        routeLogVoRouteLogExcelVoSimpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "用户信息", UserExcelVo.class, userVo);
    }

    private void deleteCache(UserAo userAo) {
        deleteUserCache(userAo.getUsername(), userAo.getMobile());
        if (userAo.getUserId() != null) {
            User one = this.getById(userAo.getUserId());
            deleteUserCache(one.getUsername(), one.getMobile());
        }
    }

    private void deleteUserCache(String username, String mobile) {
        SystemCacheUtil.deleteUserCache(username);
        if (StrUtil.isNotBlank(mobile)) {
            SystemCacheUtil.deleteUsernameByMobile(mobile);
        }
    }

    private void setUserVo(UserVo userVo) {
        Long userId = userVo.getUserId();
        List<RoleCacheVo> roles = SystemCacheUtil.getRolesByUserId(userId);
        if (CollectionUtil.isNotEmpty(roles)) {
            userVo.setRoleIds(roles.stream().map(RoleCacheVo::getRoleId).toList());
            List<String> roleNames = roles.stream().map(RoleCacheVo::getRoleName).toList();
            userVo.setRoleNames(roleNames);
            userVo.setRoleNameString(String.join(StrUtil.COMMA, roleNames));
        }
        userVo.setDeptIds(SystemCacheUtil.getDeptIdsByUserId(userId));
    }

}
