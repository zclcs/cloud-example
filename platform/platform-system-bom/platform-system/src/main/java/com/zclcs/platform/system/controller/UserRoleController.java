package com.zclcs.platform.system.controller;

import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.entity.UserRole;
import com.zclcs.platform.system.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
@Slf4j
@RestController
@RequestMapping("/user/role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;
    
    /**
     * 根据用户id查询角色id集合
     * 权限: 仅限内部调用
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    @GetMapping("/findByUserId/{userId}")
    @Inner
    public List<Long> findByUserId(@PathVariable Long userId) {
        return userRoleService.lambdaQuery().eq(UserRole::getUserId, userId).list().stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }
}