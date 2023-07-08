package com.zclcs.platform.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.bean.entity.UserDataPermission;
import com.zclcs.platform.system.mapper.UserDataPermissionMapper;
import com.zclcs.platform.system.service.UserDataPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

}
