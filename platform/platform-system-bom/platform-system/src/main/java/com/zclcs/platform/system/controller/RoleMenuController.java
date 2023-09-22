package com.zclcs.platform.system.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.RoleMenuTableDef.ROLE_MENU;

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
     * 权限: 内部调用
     *
     * @param roleId 角色id
     * @return 菜单id集合
     */
    @GetMapping("/findByRoleId/{roleId}")
    @Inner
    public List<Long> findByRoleId(@PathVariable Long roleId) {
        return this.roleMenuService.listAs(new QueryWrapper().select(ROLE_MENU.MENU_ID).where(ROLE_MENU.ROLE_ID.eq(roleId)), Long.class);
    }
}