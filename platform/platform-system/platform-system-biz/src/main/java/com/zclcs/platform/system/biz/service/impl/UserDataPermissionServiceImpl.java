package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.UserDataPermission;
import com.zclcs.platform.system.api.entity.ao.UserDataPermissionAo;
import com.zclcs.platform.system.api.entity.vo.UserDataPermissionVo;
import com.zclcs.platform.system.biz.mapper.UserDataPermissionMapper;
import com.zclcs.platform.system.biz.service.UserDataPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户数据权限关联 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class UserDataPermissionServiceImpl extends ServiceImpl<UserDataPermissionMapper, UserDataPermission> implements UserDataPermissionService {

    @Override
    public BasePage<UserDataPermissionVo> findUserDataPermissionPage(BasePageAo basePageAo, UserDataPermissionVo userDataPermissionVo) {
        BasePage<UserDataPermissionVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<UserDataPermissionVo> queryWrapper = getQueryWrapper(userDataPermissionVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<UserDataPermissionVo> findUserDataPermissionList(UserDataPermissionVo userDataPermissionVo) {
        QueryWrapper<UserDataPermissionVo> queryWrapper = getQueryWrapper(userDataPermissionVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public UserDataPermissionVo findUserDataPermission(UserDataPermissionVo userDataPermissionVo) {
        QueryWrapper<UserDataPermissionVo> queryWrapper = getQueryWrapper(userDataPermissionVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countUserDataPermission(UserDataPermissionVo userDataPermissionVo) {
        QueryWrapper<UserDataPermissionVo> queryWrapper = getQueryWrapper(userDataPermissionVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<UserDataPermissionVo> getQueryWrapper(UserDataPermissionVo userDataPermissionVo) {
        QueryWrapper<UserDataPermissionVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDataPermission createUserDataPermission(UserDataPermissionAo userDataPermissionAo) {
        UserDataPermission userDataPermission = new UserDataPermission();
        BeanUtil.copyProperties(userDataPermissionAo, userDataPermission);
        this.save(userDataPermission);
        return userDataPermission;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDataPermission updateUserDataPermission(UserDataPermissionAo userDataPermissionAo) {
        UserDataPermission userDataPermission = new UserDataPermission();
        BeanUtil.copyProperties(userDataPermissionAo, userDataPermission);
        this.updateById(userDataPermission);
        return userDataPermission;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserDataPermission(List<Long> ids) {
        this.removeByIds(ids);
    }
}
