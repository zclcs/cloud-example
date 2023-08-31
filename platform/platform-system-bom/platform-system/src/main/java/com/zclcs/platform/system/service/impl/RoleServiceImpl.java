package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.exception.MyException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zclcs.platform.system.api.bean.entity.table.RoleMenuTableDef.ROLE_MENU;
import static com.zclcs.platform.system.api.bean.entity.table.RoleTableDef.ROLE;
import static com.zclcs.platform.system.api.bean.entity.table.UserRoleTableDef.USER_ROLE;
import static com.zclcs.platform.system.api.bean.entity.table.UserTableDef.USER;

/**
 * 角色 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:28.842
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final UserService userService;

    @Override
    public BasePage<RoleVo> findRolePage(BasePageAo basePageAo, RoleVo roleVo) {
        QueryWrapper queryWrapper = getQueryWrapper(roleVo);
        Page<RoleVo> pageVo = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, RoleVo.class);
        pageVo.getRecords().forEach(vo -> {
            List<Long> menuIds = SystemCacheUtil.getMenuIdsByRoleId(vo.getRoleId());
            vo.setMenuIds(menuIds);
            vo.setMenuIdString(menuIds.stream().map(String::valueOf).collect(Collectors.joining(StrUtil.COMMA)));
        });
        return new BasePage<>(pageVo);
    }

    @Override
    public List<RoleVo> findRoleList(RoleVo roleVo) {
        QueryWrapper queryWrapper = getQueryWrapper(roleVo);
        return this.mapper.selectListByQueryAs(queryWrapper, RoleVo.class);
    }

    @Override
    public RoleVo findRole(RoleVo roleVo) {
        QueryWrapper queryWrapper = getQueryWrapper(roleVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, RoleVo.class);
    }

    @Override
    public Long countRole(RoleVo roleVo) {
        QueryWrapper queryWrapper = getQueryWrapper(roleVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(RoleVo roleVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        ROLE.ROLE_ID,
                        ROLE.ROLE_NAME,
                        ROLE.ROLE_CODE,
                        ROLE.REMARK,
                        ROLE.CREATE_AT
                )
                .where(ROLE.ROLE_NAME.likeRight(roleVo.getRoleName(), If::hasText))
                .and(ROLE.ROLE_ID.eq(roleVo.getRoleId()))
                .orderBy(ROLE.CREATE_AT.desc())
        ;
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
        List<UserRole> list = userRoleService.queryChain().where(USER_ROLE.ROLE_ID.in(role.getRoleId())).list();
        List<Long> userIdList = list.stream().map(UserRole::getUserId).toList();
        Object[] usernames = userService.queryChain().select(USER.USERNAME).where(USER.USER_ID.in(userIdList)).list().stream().map(User::getUsername).toList().toArray();
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
        List<UserRole> list = userRoleService.queryChain().where(USER_ROLE.ROLE_ID.in(role.getRoleId())).list();
        List<Long> userIdList = list.stream().map(UserRole::getUserId).toList();
        Object[] usernames = userService.queryChain().where(USER.USER_ID.in(userIdList)).list().stream().map(User::getUsername).toList().toArray();
        this.updateById(role);
        Long roleId = role.getRoleId();
        SystemCacheUtil.deleteRoleByRoleId(roleId);
        List<RoleMenu> roleMenus = getRoleMenus(role, roleAo.getMenuIds());
        this.roleMenuService.updateChain().where(ROLE_MENU.ROLE_ID.eq(roleId)).remove();

        roleMenuService.saveBatch(roleMenus);
        SystemCacheUtil.deleteMenuIdsByRoleId(roleId);

        SystemCacheUtil.deletePermissionsByUsernames(usernames);
        SystemCacheUtil.deleteRoutersByUsernames(usernames);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(List<Long> ids) {
        List<UserRole> list = userRoleService.queryChain().where(USER_ROLE.ROLE_ID.in(ids)).list();
        List<Long> userIdList = list.stream().map(UserRole::getUserId).toList();
        Object[] userIds = userIdList.toArray();
        Object[] usernames = userService.queryChain().where(USER.USER_ID.in(userIdList)).list().stream().map(User::getUsername).toList().toArray();
        this.removeByIds(ids);
        Object[] roleIds = ids.toArray();
        SystemCacheUtil.deleteRoleByRoleIds(roleIds);

        this.roleMenuService.updateChain().where(ROLE_MENU.ROLE_ID.in(ids)).remove();
        SystemCacheUtil.deleteMenuIdsByRoleIds(roleIds);

        this.userRoleService.updateChain().where(USER_ROLE.ROLE_ID.in(ids)).remove();
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
        Role one = this.queryChain().where(ROLE.ROLE_CODE.eq(roleCode)).one();
        if (one != null && !one.getRoleId().equals(roleId)) {
            throw new MyException("角色编码重复");
        }
    }

    @Override
    public void validateRoleName(String roleName, Long roleId) {
        Role one = this.queryChain().where(ROLE.ROLE_NAME.eq(roleName)).one();
        if (one != null && !one.getRoleId().equals(roleId)) {
            throw new MyException("角色名称重复");
        }
    }
}
