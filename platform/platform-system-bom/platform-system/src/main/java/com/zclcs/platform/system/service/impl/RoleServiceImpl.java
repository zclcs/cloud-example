package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.bean.ao.RoleAo;
import com.zclcs.platform.system.api.bean.entity.Role;
import com.zclcs.platform.system.api.bean.entity.RoleMenu;
import com.zclcs.platform.system.api.bean.entity.User;
import com.zclcs.platform.system.api.bean.entity.UserRole;
import com.zclcs.platform.system.api.bean.vo.RoleVo;
import com.zclcs.platform.system.mapper.RoleMapper;
import com.zclcs.platform.system.service.RoleMenuService;
import com.zclcs.platform.system.service.RoleService;
import com.zclcs.platform.system.service.UserRoleService;
import com.zclcs.platform.system.service.UserService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:28.842
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final UserService userService;

    @Override
    public BasePage<RoleVo> findRolePage(BasePageAo basePageAo, RoleVo roleVo) {
        BasePage<RoleVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        BasePage<RoleVo> pageVo = this.mapper.findPageVo(basePage, queryWrapper);
        pageVo.getList().forEach(vo -> {
            List<Long> menuIds = SystemCacheUtil.getMenuIdsByRoleId(vo.getRoleId());
            vo.setMenuIds(menuIds);
            vo.setMenuIdString(menuIds.stream().map(String::valueOf).collect(Collectors.joining(StrUtil.COMMA)));
        });
        return pageVo;
    }

    @Override
    public List<RoleVo> findRoleList(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        return this.mapper.findListVo(queryWrapper);
    }

    @Override
    public RoleVo findRole(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        return this.mapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRole(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        return this.mapper.countVo(queryWrapper);
    }

    private QueryWrapper<RoleVo> getQueryWrapper(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeRightNotBlank(queryWrapper, "tb.role_name", roleVo.getRoleName());
        QueryWrapperUtil.eqNotNull(queryWrapper, "tb.role_id", roleVo.getRoleId());
        queryWrapper
                .orderByDesc("tb.create_at");
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role createRole(RoleAo roleAo) {
        validateRoleCode(roleAo.getRoleCode(), roleAo.getRoleId());
        validateRoleName(roleAo.getRoleName(), roleAo.getRoleId());
        Role role = new Role();
        BeanUtil.copyProperties(roleAo, role);
        this.save(role);
        Long roleId = role.getRoleId();
        List<UserRole> list = userRoleService.lambdaQuery().in(UserRole::getRoleId, roleId).list();
        List<Long> userIdList = list.stream().map(UserRole::getUserId).toList();
        Object[] usernames = userService.lambdaQuery().in(User::getUserId, userIdList).select(User::getUsername).list().stream().map(User::getUsername).toList().toArray();
        SystemCacheUtil.deleteRoleByRoleId(roleId);
        List<RoleMenu> roleMenus = getRoleMenus(role, roleAo.getMenuIds());
        roleMenuService.saveBatch(roleMenus);
        SystemCacheUtil.deleteMenuIdsByRoleId(roleId);

        SystemCacheUtil.deletePermissionsByUsernames(usernames);
        SystemCacheUtil.deleteRoutersByUsernames(usernames);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role updateRole(RoleAo roleAo) {
        validateRoleName(roleAo.getRoleName(), roleAo.getRoleId());
        Role role = new Role();
        BeanUtil.copyProperties(roleAo, role);
        role.setRoleCode(null);
        List<UserRole> list = userRoleService.lambdaQuery().in(UserRole::getRoleId, role.getRoleId()).list();
        List<Long> userIdList = list.stream().map(UserRole::getUserId).toList();
        Object[] usernames = userService.lambdaQuery().in(User::getUserId, userIdList).list().stream().map(User::getUsername).toList().toArray();
        this.updateById(role);
        Long roleId = role.getRoleId();
        SystemCacheUtil.deleteRoleByRoleId(roleId);
        List<RoleMenu> roleMenus = getRoleMenus(role, roleAo.getMenuIds());
        this.roleMenuService.lambdaUpdate().eq(RoleMenu::getRoleId, roleId).remove();

        roleMenuService.saveBatch(roleMenus);
        SystemCacheUtil.deleteMenuIdsByRoleId(roleId);

        SystemCacheUtil.deletePermissionsByUsernames(usernames);
        SystemCacheUtil.deleteRoutersByUsernames(usernames);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(List<Long> ids) {
        List<UserRole> list = userRoleService.lambdaQuery().in(UserRole::getRoleId, ids).list();
        List<Long> userIdList = list.stream().map(UserRole::getUserId).toList();
        Object[] userIds = userIdList.toArray();
        Object[] usernames = userService.lambdaQuery().in(User::getUserId, userIdList).list().stream().map(User::getUsername).toList().toArray();
        this.removeByIds(ids);
        Object[] roleIds = ids.toArray();
        SystemCacheUtil.deleteRoleByRoleIds(roleIds);

        this.roleMenuService.lambdaUpdate().in(RoleMenu::getRoleId, ids).remove();
        SystemCacheUtil.deleteMenuIdsByRoleIds(roleIds);

        this.userRoleService.lambdaUpdate().in(UserRole::getRoleId, ids).remove();
        SystemCacheUtil.deleteRoleIdsByUserIds(userIds);

        SystemCacheUtil.deletePermissionsByUsernames(usernames);
        SystemCacheUtil.deleteRoutersByUsernames(usernames);
    }

    private List<RoleMenu> getRoleMenus(Role role, List<Long> menuIds) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        menuIds.stream().filter(Objects::nonNull).forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(role.getRoleId());
            roleMenus.add(roleMenu);
        });
        return roleMenus;
    }

    @Override
    public void validateRoleCode(String roleCode, Long roleId) {
        if (CommonCore.TOP_PARENT_CODE.equals(roleCode)) {
            throw new MyException("角色编码输入非法值");
        }
        Role one = this.lambdaQuery().eq(Role::getRoleCode, roleCode).one();
        if (one != null && !one.getRoleId().equals(roleId)) {
            throw new MyException("角色编码重复");
        }
    }

    @Override
    public void validateRoleName(String roleName, Long roleId) {
        Role one = this.lambdaQuery().eq(Role::getRoleName, roleName).one();
        if (one != null && !one.getRoleId().equals(roleId)) {
            throw new MyException("角色名称重复");
        }
    }
}
