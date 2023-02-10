package com.zclcs.platform.system.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.entity.UserDataPermission;
import com.zclcs.platform.system.biz.mapper.UserDataPermissionMapper;
import com.zclcs.platform.system.biz.service.UserDataPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户数据权限关联 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDataPermissionServiceImpl extends ServiceImpl<UserDataPermissionMapper, UserDataPermission> implements UserDataPermissionService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDeptIds(List<Long> deptIds) {
        this.lambdaUpdate().in(UserDataPermission::getDeptId, deptIds).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserIds(List<Long> userIds) {
        this.lambdaUpdate().in(UserDataPermission::getUserId, userIds).remove();
    }

    @Override
    public List<Long> findByUserId(Long userId) {
        return this.lambdaQuery().eq(UserDataPermission::getUserId, userId).list().stream().map(UserDataPermission::getDeptId).collect(Collectors.toList());
    }
}
