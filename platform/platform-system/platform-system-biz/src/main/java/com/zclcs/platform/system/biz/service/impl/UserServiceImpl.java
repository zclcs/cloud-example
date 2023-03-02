package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.common.security.starter.utils.PasswordUtil;
import com.zclcs.common.security.starter.utils.SecurityUtil;
import com.zclcs.platform.system.api.entity.User;
import com.zclcs.platform.system.api.entity.UserDataPermission;
import com.zclcs.platform.system.api.entity.UserRole;
import com.zclcs.platform.system.api.entity.ao.UserAo;
import com.zclcs.platform.system.api.entity.vo.UserMobileVo;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import com.zclcs.platform.system.biz.mapper.UserMapper;
import com.zclcs.platform.system.biz.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private final MenuService menuService;
    private final UserDataPermissionService userDataPermissionService;
    private final DeptService deptService;
    private final RedisService redisService;

    @Override
    public BasePage<UserVo> findUserPage(BasePageAo basePageAo, UserVo userVo) {
        BasePage<UserVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        basePage.getList().forEach(systemUserVo -> {
            systemUserVo.setRoleIds(StrUtil.split(systemUserVo.getRoleIdString(), StrUtil.COMMA).stream().map(Long::valueOf).collect(Collectors.toList()));
            systemUserVo.setDeptIds(StrUtil.split(systemUserVo.getDeptIdString(), StrUtil.COMMA).stream().map(Long::valueOf).collect(Collectors.toList()));
        });
        return this.baseMapper.findPageVo(basePage, queryWrapper);
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
        QueryWrapperUtil.eqNotBlank(queryWrapper, "t1.username", username);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public UserMobileVo findByMobile(String mobile) {
        User one = this.lambdaQuery().eq(User::getMobile, mobile).one();
        UserMobileVo userMobileVo = null;
        if (one != null) {
            userMobileVo = new UserMobileVo();
            BeanUtil.copyProperties(one, userMobileVo);
        }
        return userMobileVo;
    }

    private QueryWrapper<UserVo> getQueryWrapper(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = new QueryWrapper<>();
        AtomicReference<List<Long>> deptList = new AtomicReference<>();
        Optional.ofNullable(userVo.getDeptId()).ifPresent(deptId -> {
            List<Long> childDeptId = deptService.getChildDeptId(deptId);
            if (CollectionUtil.isNotEmpty(childDeptId)) {
                deptList.set(childDeptId);
            } else {
                deptList.set(CollectionUtil.newArrayList(0L));
            }
        });
        QueryWrapperUtil.inNotEmpty(queryWrapper, "t1.dept_id", deptList.get());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "t1.username", userVo.getUsername());
        return queryWrapper;
    }

    @Override
    public UserVo findUserDetail(String username) {
        Object obj = redisService.get(RedisCachePrefixConstant.USER + username);
        if (obj == null) {
            synchronized (this) {
                // 再查一次，防止上个已经抢到锁的线程已经更新过了
                obj = redisService.get(RedisCachePrefixConstant.USER + username);
                if (obj != null) {
                    return (UserVo) obj;
                }
                return cacheAndGetUserDetail(username);
            }
        }
        return (UserVo) obj;
    }

    @Override
    public UserVo cacheAndGetUserDetail(String username) {
        UserVo userVo = this.findByName(username);
        if (userVo == null) {
            return null;
        }
        userVo.setRoleNames(StrUtil.split(userVo.getRoleNameString(), StrUtil.COMMA));
        userVo.setRoleIds(StrUtil.split(userVo.getRoleIdString(), StrUtil.COMMA).stream().map(Long::valueOf).collect(Collectors.toList()));
        userVo.setPermissions(menuService.findUserPermissions(username));
        redisService.set(RedisCachePrefixConstant.USER + username, userVo);
        return userVo;
    }

    @Override
    public void deleteUserDetailCache(String username) {
        redisService.del(RedisCachePrefixConstant.USER + username);
    }

    @Override
    public UserVo findUserDetailByMobile(String mobile) {
        Object obj = redisService.get(RedisCachePrefixConstant.USER_MOBILE + mobile);
        if (obj == null) {
            synchronized (this) {
                // 再查一次，防止上个已经抢到锁的线程已经更新过了
                obj = redisService.get(RedisCachePrefixConstant.USER_MOBILE + mobile);
                if (obj != null) {
                    UserMobileVo userMobileVo = (UserMobileVo) obj;
                    return (UserVo) redisService.get(RedisCachePrefixConstant.USER + userMobileVo.getUsername());
                }
                return cacheAndGetUserDetailByMobile(mobile);
            }
        }
        UserMobileVo userMobileVo = (UserMobileVo) obj;
        return (UserVo) redisService.get(RedisCachePrefixConstant.USER + userMobileVo.getUsername());
    }

    @Override
    public UserVo cacheAndGetUserDetailByMobile(String mobile) {
        UserMobileVo byMobile = this.findByMobile(mobile);
        if (byMobile == null) {
            return null;
        }
        UserVo userVo = this.findByName(byMobile.getUsername());
        userVo.setPermissions(menuService.findUserPermissions(userVo.getUsername()));
        redisService.set(RedisCachePrefixConstant.USER + userVo.getUsername(), userVo);
        redisService.set(RedisCachePrefixConstant.USER_MOBILE + userVo.getMobile(), userVo);
        return userVo;
    }

    @Override
    public void deleteUserDetailCacheByMobile(String mobile) {
        UserMobileVo o = (UserMobileVo) redisService.get(RedisCachePrefixConstant.USER_MOBILE + mobile);
        if (o != null) {
            redisService.del(RedisCachePrefixConstant.USER_MOBILE + mobile);
            redisService.del(RedisCachePrefixConstant.USER + o.getUsername());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(UserAo userAo) {
        User user = new User();
        BeanUtil.copyProperties(userAo, user);
        this.save(user);
        user.setAvatar(MyConstant.DEFAULT_AVATAR);
        user.setPassword(PasswordUtil.PASSWORD_ENCODER.encode(MyConstant.DEFAULT_PASSWORD));
        List<UserRole> userRoles = getUserRoles(user, userAo.getRoleIds());
        userRoleService.saveBatch(userRoles);
        List<UserDataPermission> userDataPermissions = getUserDataPermissions(user, userAo.getDeptIds());
        userDataPermissionService.saveBatch(userDataPermissions);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(UserAo userAo) {
        User user = new User();
        BeanUtil.copyProperties(userAo, user);
        // 更新用户
        user.setPassword(null);
        user.setUsername(null);
        updateById(user);

        ArrayList<Long> userIds = CollUtil.newArrayList(user.getUserId());
        List<UserRole> userRoles = getUserRoles(user, userAo.getRoleIds());
        userRoleService.saveBatch(userRoles);

        userDataPermissionService.deleteByUserIds(userIds);
        List<UserDataPermission> userDataPermissions = getUserDataPermissions(user, userAo.getDeptIds());
        userDataPermissionService.saveBatch(userDataPermissions);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(List<Long> ids) {
        this.removeByIds(ids);
        // 删除用户角色
        this.userRoleService.deleteUserRolesByUserId(ids);
        this.userDataPermissionService.deleteByUserIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginTime(String username) {
        this.lambdaUpdate().eq(User::getUsername, username).set(User::getLastLoginTime, DateUtil.date()).update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String username, String password) {
        String currentUsername = SecurityUtil.getUsername();
        this.lambdaUpdate().eq(User::getUsername, Optional.ofNullable(username).filter(StrUtil::isNotBlank).orElse(currentUsername))
                .set(User::getPassword, password).update();
    }

    @Override
    public void updateStatus(String username, String status) {
        String orElse = Optional.ofNullable(status).filter(StrUtil::isNotBlank).orElse(MyConstant.STATUS_LOCK);
        String currentUsername = SecurityUtil.getUsername();
        this.lambdaUpdate().eq(User::getUsername, Optional.ofNullable(username).filter(StrUtil::isNotBlank).orElse(currentUsername))
                .set(User::getStatus, orElse)
                .update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(List<String> usernames) {
        this.lambdaUpdate().in(User::getUsername, usernames)
                .set(User::getPassword, PasswordUtil.PASSWORD_ENCODER.encode(MyConstant.DEFAULT_PASSWORD)).update();
    }

    private List<UserRole> getUserRoles(User user, List<Long> roles) {
        List<UserRole> userRoles = new ArrayList<>();
        roles.stream().filter(Objects::nonNull).forEach(roleId -> {
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
            permission.setDeptId(deptId);
            permission.setUserId(user.getUserId());
            userDataPermissions.add(permission);
        });
        return userDataPermissions;
    }
}
