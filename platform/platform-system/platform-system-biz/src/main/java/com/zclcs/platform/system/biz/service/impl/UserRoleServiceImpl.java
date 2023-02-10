package com.zclcs.platform.system.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.entity.UserRole;
import com.zclcs.platform.system.biz.mapper.UserRoleMapper;
import com.zclcs.platform.system.biz.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:38.682
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRolesByRoleId(List<Long> roleIds) {
        this.lambdaUpdate().in(UserRole::getRoleId, roleIds).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRolesByUserId(List<Long> userIds) {
        this.lambdaUpdate().in(UserRole::getUserId, userIds).remove();
    }

    @Override
    public List<Long> findUserIdsByRoleId(List<Long> roleIds) {
        return this.lambdaQuery().in(UserRole::getRoleId, roleIds).list().stream().map(UserRole::getUserId).collect(Collectors.toList());
    }
}
