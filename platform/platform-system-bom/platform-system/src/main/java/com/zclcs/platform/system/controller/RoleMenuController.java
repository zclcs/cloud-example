package com.zclcs.platform.system.controller;

import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.entity.RoleMenu;
import com.zclcs.platform.system.service.RoleMenuService;
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
 * @since 2023-01-10 10:39:28.842
 */
@Slf4j
@RestController
@RequestMapping("/role/menu")
@RequiredArgsConstructor
public class RoleMenuController {

    private final RoleMenuService roleMenuService;


    /**
     * 根据角色id查询菜单id集合
     *
     * @param roleId 角色id
     * @return 菜单id集合
     */
    @GetMapping("/findByRoleId/{roleId}")
    @Inner
    public List<Long> findByRoleId(@PathVariable Long roleId) {
        return roleMenuService.lambdaQuery().eq(RoleMenu::getRoleId, roleId).list().stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }
}