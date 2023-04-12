package com.zclcs.platform.system.controller;

import com.zclcs.common.security.starter.annotation.Inner;
import com.zclcs.platform.system.api.entity.RoleMenu;
import com.zclcs.platform.system.service.RoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
@Slf4j
@RestController
@RequestMapping("/role/menu")
@RequiredArgsConstructor
@Tag(name = "角色菜单关联")
public class RoleMenuController {

    private final RoleMenuService roleMenuService;


    @GetMapping("/findByRoleId/{roleId}")
    @Operation(summary = "根据角色id查询菜单id")
    @Inner
    public List<Long> findByRoleId(@PathVariable Long roleId) {
        return roleMenuService.lambdaQuery().eq(RoleMenu::getRoleId, roleId).list().stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }
}