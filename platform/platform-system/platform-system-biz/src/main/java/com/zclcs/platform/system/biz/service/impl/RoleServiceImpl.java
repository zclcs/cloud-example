package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.Role;
import com.zclcs.platform.system.api.entity.RoleMenu;
import com.zclcs.platform.system.api.entity.ao.RoleAo;
import com.zclcs.platform.system.api.entity.vo.RoleVo;
import com.zclcs.platform.system.biz.mapper.RoleMapper;
import com.zclcs.platform.system.biz.service.RoleMenuService;
import com.zclcs.platform.system.biz.service.RoleService;
import com.zclcs.platform.system.biz.service.UserRoleService;
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
 * @date 2023-01-10 10:39:28.842
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final RedisService redisService;

    @Override
    public BasePage<RoleVo> findRolePage(BasePageAo basePageAo, RoleVo roleVo) {
        BasePage<RoleVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        basePage.getList().forEach(systemRoleVo ->
                systemRoleVo.setMenuIds(
                        StrUtil.split(systemRoleVo.getMenuIdsString(), StrUtil.COMMA)
                                .stream().map(Long::valueOf).collect(Collectors.toList())));
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RoleVo> findRoleList(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public RoleVo findRole(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRole(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    @Override
    public RoleVo findById(Long roleId) {
        Object obj = redisService.get(RedisCachePrefixConstant.ROLE + roleId);
        if (obj == null) {
            synchronized (this) {
                // 再查一次，防止上个已经抢到锁的线程已经更新过了
                obj = redisService.get(RedisCachePrefixConstant.ROLE + roleId);
                if (obj != null) {
                    return (RoleVo) obj;
                }
                return cacheAndGetById(roleId);
            }
        }
        return (RoleVo) obj;
    }

    @Override
    public RoleVo cacheAndGetById(Long roleId) {
        RoleVo systemRoleVo = this.findRole(RoleVo.builder().roleId(roleId).build());
        List<String> usernames = this.selectUsernamesByRoleId(roleId);
        systemRoleVo.setUsernames(usernames);
        redisService.set(RedisCachePrefixConstant.ROLE + roleId, systemRoleVo);
        return systemRoleVo;
    }

    @Override
    public void deleteCacheById(Long roleId) {
        redisService.del(String.valueOf(roleId));
    }

    @Override
    public List<String> selectUsernamesByRoleId(Long roleId) {
        return this.baseMapper.selectUsernamesByRoleId(roleId);
    }

    private QueryWrapper<RoleVo> getQueryWrapper(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "t1.role_name", roleVo.getRoleName());
        QueryWrapperUtil.eqNotNull(queryWrapper, "t1.role_id", roleVo.getRoleId());
        queryWrapper
                .orderByDesc("t1.create_at");
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role createRole(RoleAo roleAo) {
        Role role = new Role();
        BeanUtil.copyProperties(roleAo, role);
        this.save(role);
        List<RoleMenu> roleMenus = getRoleMenus(role, roleAo.getMenuIds());
        roleMenuService.saveBatch(roleMenus);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role updateRole(RoleAo roleAo) {
        Role role = new Role();
        BeanUtil.copyProperties(roleAo, role);
        this.updateById(role);
        ArrayList<Long> roleIds = CollectionUtil.newArrayList(role.getRoleId());
        this.roleMenuService.deleteRoleMenusByRoleId(roleIds);
        List<RoleMenu> roleMenus = getRoleMenus(role, roleAo.getMenuIds());
        roleMenuService.saveBatch(roleMenus);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(List<Long> ids) {
        this.removeByIds(ids);
        this.roleMenuService.deleteRoleMenusByRoleId(ids);
        this.userRoleService.deleteUserRolesByRoleId(ids);
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
}
