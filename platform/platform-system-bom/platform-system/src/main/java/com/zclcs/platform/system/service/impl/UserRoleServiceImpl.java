package com.zclcs.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.bean.entity.UserRole;
import com.zclcs.platform.system.mapper.UserRoleMapper;
import com.zclcs.platform.system.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:38.682
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
