package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.UserRole;
import com.zclcs.platform.system.api.entity.ao.UserRoleAo;
import com.zclcs.platform.system.api.entity.vo.UserRoleVo;
import com.zclcs.platform.system.biz.mapper.UserRoleMapper;
import com.zclcs.platform.system.biz.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户角色关联 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:38.682
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public BasePage<UserRoleVo> findUserRolePage(BasePageAo basePageAo, UserRoleVo userRoleVo) {
        BasePage<UserRoleVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<UserRoleVo> queryWrapper = getQueryWrapper(userRoleVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<UserRoleVo> findUserRoleList(UserRoleVo userRoleVo) {
        QueryWrapper<UserRoleVo> queryWrapper = getQueryWrapper(userRoleVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public UserRoleVo findUserRole(UserRoleVo userRoleVo) {
        QueryWrapper<UserRoleVo> queryWrapper = getQueryWrapper(userRoleVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countUserRole(UserRoleVo userRoleVo) {
        QueryWrapper<UserRoleVo> queryWrapper = getQueryWrapper(userRoleVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<UserRoleVo> getQueryWrapper(UserRoleVo userRoleVo) {
        QueryWrapper<UserRoleVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserRole createUserRole(UserRoleAo userRoleAo) {
        UserRole userRole = new UserRole();
        BeanUtil.copyProperties(userRoleAo, userRole);
        this.save(userRole);
        return userRole;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserRole updateUserRole(UserRoleAo userRoleAo) {
        UserRole userRole = new UserRole();
        BeanUtil.copyProperties(userRoleAo, userRole);
        this.updateById(userRole);
        return userRole;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRole(List<Long> ids) {
        this.removeByIds(ids);
    }
}
