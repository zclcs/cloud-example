package com.zclcs.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.platform.system.api.bean.entity.RoleMenu;
import com.zclcs.platform.system.mapper.RoleMenuMapper;
import com.zclcs.platform.system.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关联 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:23.376
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}
