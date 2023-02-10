package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.common.security.starter.utils.SecurityUtil;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.entity.ao.MenuAo;
import com.zclcs.platform.system.api.entity.router.VueRouter;
import com.zclcs.platform.system.api.entity.vo.MenuTreeVo;
import com.zclcs.platform.system.api.entity.vo.MenuVo;
import com.zclcs.platform.system.biz.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:18.238
 */
@Slf4j
@RestController
@RequestMapping("menu")
@RequiredArgsConstructor
@Tag(name = "菜单")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "菜单查询（分页）")
    @PreAuthorize("hasAuthority('menu:view')")
    public BaseRsp<BasePage<MenuVo>> findMenuPage(@Validated BasePageAo basePageAo, @Validated MenuVo menuVo) {
        BasePage<MenuVo> page = this.menuService.findMenuPage(basePageAo, menuVo);
        return RspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "菜单查询（集合）")
    @PreAuthorize("hasAuthority('menu:view')")
    public BaseRsp<List<MenuVo>> findMenuList(@Validated MenuVo menuVo) {
        List<MenuVo> list = this.menuService.findMenuList(menuVo);
        return RspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "菜单查询（单个）")
    @PreAuthorize("hasAuthority('menu:view')")
    public BaseRsp<MenuVo> findMenu(@Validated MenuVo menuVo) {
        MenuVo menu = this.menuService.findMenu(menuVo);
        return RspUtil.data(menu);
    }

    @GetMapping("/routers")
    @Operation(summary = "用户路由")
    public BaseRsp<List<VueRouter<MenuVo>>> getUserRouters() {
        List<VueRouter<MenuVo>> userRouters = this.menuService.findUserRouters(SecurityUtil.getUsername());
        return RspUtil.data(userRouters);
    }

    @GetMapping("/tree")
    @Operation(summary = "菜单")
    public BaseRsp<List<MenuTreeVo>> menuList(MenuVo menu) {
        List<MenuTreeVo> systemMenus = this.menuService.findSystemMenus(menu);
        return RspUtil.data(systemMenus);
    }

    @GetMapping("/permissions")
    @Operation(summary = "权限")
    public BaseRsp<List<String>> findUserPermissions() {
        List<String> userPermissions = this.menuService.findUserPermissions(SecurityUtil.getUsername());
        return RspUtil.data(userPermissions);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('menu:add')")
    @ControllerEndpoint(operation = "新增菜单")
    @Operation(summary = "新增菜单")
    public BaseRsp<Menu> addMenu(@RequestBody @Validated MenuAo menuAo) {
        return RspUtil.data(this.menuService.createMenu(menuAo));
    }

    @DeleteMapping("/{menuIds}")
    @PreAuthorize("hasAuthority('menu:delete')")
    @ControllerEndpoint(operation = "删除菜单")
    @Operation(summary = "删除菜单")
    @Parameters({
            @Parameter(name = "menuIds", description = "菜单id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteMenu(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        List<Long> ids = Arrays.stream(menuIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.menuService.deleteMenu(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('menu:update')")
    @ControllerEndpoint(operation = "修改菜单")
    @Operation(summary = "修改菜单")
    public BaseRsp<Menu> updateMenu(@RequestBody @Validated(UpdateStrategy.class) MenuAo menuAo) {
        return RspUtil.data(this.menuService.updateMenu(menuAo));
    }
}