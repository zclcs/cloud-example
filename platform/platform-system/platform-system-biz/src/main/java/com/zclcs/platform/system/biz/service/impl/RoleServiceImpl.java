package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.Role;
import com.zclcs.platform.system.api.entity.ao.RoleAo;
import com.zclcs.platform.system.api.entity.vo.RoleVo;
import com.zclcs.platform.system.biz.mapper.RoleMapper;
import com.zclcs.platform.system.biz.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public BasePage<RoleVo> findRolePage(BasePageAo basePageAo, RoleVo roleVo) {
        BasePage<RoleVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RoleVo> findRoleList(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public RoleVo findRole(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRole(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = getQueryWrapper(roleVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<RoleVo> getQueryWrapper(RoleVo roleVo) {
        QueryWrapper<RoleVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role createRole(RoleAo roleAo) {
        Role role = new Role();
        BeanUtil.copyProperties(roleAo, role);
        this.save(role);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role updateRole(RoleAo roleAo) {
        Role role = new Role();
        BeanUtil.copyProperties(roleAo, role);
        this.updateById(role);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(List<Long> ids) {
        this.removeByIds(ids);
    }
}
