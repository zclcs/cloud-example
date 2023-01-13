package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.RoleMenu;
import com.zclcs.platform.system.api.entity.ao.RoleMenuAo;
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
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public BasePage<RoleMenuVo> findRoleMenuPage(BasePageAo basePageAo, RoleMenuVo roleMenuVo) {
        BasePage<RoleMenuVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RoleMenuVo> queryWrapper = getQueryWrapper(roleMenuVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RoleMenuVo> findRoleMenuList(RoleMenuVo roleMenuVo) {
        QueryWrapper<RoleMenuVo> queryWrapper = getQueryWrapper(roleMenuVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public RoleMenuVo findRoleMenu(RoleMenuVo roleMenuVo) {
        QueryWrapper<RoleMenuVo> queryWrapper = getQueryWrapper(roleMenuVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRoleMenu(RoleMenuVo roleMenuVo) {
        QueryWrapper<RoleMenuVo> queryWrapper = getQueryWrapper(roleMenuVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<RoleMenuVo> getQueryWrapper(RoleMenuVo roleMenuVo) {
        QueryWrapper<RoleMenuVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleMenu createRoleMenu(RoleMenuAo roleMenuAo) {
        RoleMenu roleMenu = new RoleMenu();
        BeanUtil.copyProperties(roleMenuAo, roleMenu);
        this.save(roleMenu);
        return roleMenu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleMenu updateRoleMenu(RoleMenuAo roleMenuAo) {
        RoleMenu roleMenu = new RoleMenu();
        BeanUtil.copyProperties(roleMenuAo, roleMenu);
        this.updateById(roleMenu);
        return roleMenu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenu(List<Long> ids) {
        this.removeByIds(ids);
    }
}
