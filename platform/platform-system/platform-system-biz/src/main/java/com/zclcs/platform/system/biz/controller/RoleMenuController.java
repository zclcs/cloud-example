package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.RoleMenu;
import com.zclcs.platform.system.api.entity.ao.RoleMenuAo;
import com.zclcs.platform.system.api.entity.vo.RoleMenuVo;
import com.zclcs.platform.system.biz.service.RoleMenuService;
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
 * 角色菜单关联 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:23.376
 */
@Slf4j
@RestController
@RequestMapping("roleMenu")
@RequiredArgsConstructor
@Tag(name = "角色菜单关联")
public class RoleMenuController {

    private final RoleMenuService roleMenuService;

    @GetMapping
    @Operation(summary = "角色菜单关联查询（分页）")
    @PreAuthorize("hasAuthority('roleMenu:view')")
    public BaseRsp<BasePage<RoleMenuVo>> findRoleMenuPage(@Validated BasePageAo basePageAo, @Validated RoleMenuVo roleMenuVo) {
        BasePage<RoleMenuVo> page = this.roleMenuService.findRoleMenuPage(basePageAo, roleMenuVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "角色菜单关联查询（集合）")
    @PreAuthorize("hasAuthority('roleMenu:view')")
    public BaseRsp<List<RoleMenuVo>> findRoleMenuList(@Validated RoleMenuVo roleMenuVo) {
        List<RoleMenuVo> list = this.roleMenuService.findRoleMenuList(roleMenuVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "角色菜单关联查询（单个）")
    @PreAuthorize("hasAuthority('roleMenu:view')")
    public BaseRsp<RoleMenuVo> findRoleMenu(@Validated RoleMenuVo roleMenuVo) {
        RoleMenuVo roleMenu = this.roleMenuService.findRoleMenu(roleMenuVo);
        return BaseRspUtil.data(roleMenu);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('roleMenu:add')")
    @ControllerEndpoint(operation = "新增角色菜单关联")
    @Operation(summary = "新增角色菜单关联")
    public BaseRsp<RoleMenu> addRoleMenu(@RequestBody @Validated RoleMenuAo roleMenuAo) {
        return BaseRspUtil.data(this.roleMenuService.createRoleMenu(roleMenuAo));
    }

    @DeleteMapping("/{roleMenuIds}")
    @PreAuthorize("hasAuthority('roleMenu:delete')")
    @ControllerEndpoint(operation = "删除角色菜单关联")
    @Operation(summary = "删除角色菜单关联")
    @Parameters({
            @Parameter(name = "roleMenuIds", description = "角色菜单关联id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRoleMenu(@NotBlank(message = "{required}") @PathVariable String roleMenuIds) {
        List<Long> ids = Arrays.stream(roleMenuIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.roleMenuService.deleteRoleMenu(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('roleMenu:update')")
    @ControllerEndpoint(operation = "修改角色菜单关联")
    @Operation(summary = "修改角色菜单关联")
    public BaseRsp<RoleMenu> updateRoleMenu(@RequestBody @Validated(UpdateStrategy.class) RoleMenuAo roleMenuAo) {
        return BaseRspUtil.data(this.roleMenuService.updateRoleMenu(roleMenuAo));
    }
}