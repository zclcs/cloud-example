package com.zclcs.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.bean.entity.UserDataPermission;
import com.zclcs.platform.system.mapper.UserDataPermissionMapper;
import com.zclcs.platform.system.service.UserDataPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户数据权限关联 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:43.325
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDataPermissionServiceImpl extends ServiceImpl<UserDataPermissionMapper, UserDataPermission> implements UserDataPermissionService {

}
