package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.User;
import com.zclcs.platform.system.api.entity.ao.UserAo;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import com.zclcs.platform.system.biz.mapper.UserMapper;
import com.zclcs.platform.system.biz.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public BasePage<UserVo> findUserPage(BasePageAo basePageAo, UserVo userVo) {
        BasePage<UserVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<UserVo> findUserList(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public UserVo findUser(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countUser(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = getQueryWrapper(userVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<UserVo> getQueryWrapper(UserVo userVo) {
        QueryWrapper<UserVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(UserAo userAo) {
        User user = new User();
        BeanUtil.copyProperties(userAo, user);
        this.save(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(UserAo userAo) {
        User user = new User();
        BeanUtil.copyProperties(userAo, user);
        this.updateById(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(List<Long> ids) {
        this.removeByIds(ids);
    }
}
