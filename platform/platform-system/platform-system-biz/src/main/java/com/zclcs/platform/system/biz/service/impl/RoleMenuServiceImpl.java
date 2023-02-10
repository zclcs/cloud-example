package com.zclcs.platform.system.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.entity.RoleMenu;
import com.zclcs.platform.system.api.entity.vo.RoleMenuVo;
import com.zclcs.platform.system.biz.mapper.RoleMenuMapper;
import com.zclcs.platform.system.biz.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色菜单关联 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:23.376
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleId(List<Long> roleIds) {
        this.lambdaUpdate().in(RoleMenu::getRoleId, roleIds).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByMenuId(List<Long> menuIds) {
        this.lambdaUpdate().in(RoleMenu::getMenuId, menuIds).remove();
    }

    @Override
    public List<RoleMenuVo> getRoleMenusByRoleId(Long roleId) {
        QueryWrapper<RoleMenuVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sr.role_id", roleId);
        return this.baseMapper.findListVo(queryWrapper);
    }
}
