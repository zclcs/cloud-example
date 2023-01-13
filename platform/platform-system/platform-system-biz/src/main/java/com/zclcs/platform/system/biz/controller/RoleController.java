package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.Role;
import com.zclcs.platform.system.api.entity.ao.RoleAo;
import com.zclcs.platform.system.api.entity.vo.RoleVo;
import com.zclcs.platform.system.biz.service.RoleService;
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
 * 角色 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
@Slf4j
@RestController
@RequestMapping("role")
@RequiredArgsConstructor
@Tag(name = "角色")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "角色查询（分页）")
    @PreAuthorize("hasAuthority('role:view')")
    public BaseRsp<BasePage<RoleVo>> findRolePage(@Validated BasePageAo basePageAo, @Validated RoleVo roleVo) {
        BasePage<RoleVo> page = this.roleService.findRolePage(basePageAo, roleVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "角色查询（集合）")
    @PreAuthorize("hasAuthority('role:view')")
    public BaseRsp<List<RoleVo>> findRoleList(@Validated RoleVo roleVo) {
        List<RoleVo> list = this.roleService.findRoleList(roleVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "角色查询（单个）")
    @PreAuthorize("hasAuthority('role:view')")
    public BaseRsp<RoleVo> findRole(@Validated RoleVo roleVo) {
        RoleVo role = this.roleService.findRole(roleVo);
        return BaseRspUtil.data(role);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role:add')")
    @ControllerEndpoint(operation = "新增角色")
    @Operation(summary = "新增角色")
    public BaseRsp<Role> addRole(@RequestBody @Validated RoleAo roleAo) {
        return BaseRspUtil.data(this.roleService.createRole(roleAo));
    }

    @DeleteMapping("/{roleIds}")
    @PreAuthorize("hasAuthority('role:delete')")
    @ControllerEndpoint(operation = "删除角色")
    @Operation(summary = "删除角色")
    @Parameters({
            @Parameter(name = "roleIds", description = "角色id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRole(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        List<Long> ids = Arrays.stream(roleIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.roleService.deleteRole(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('role:update')")
    @ControllerEndpoint(operation = "修改角色")
    @Operation(summary = "修改角色")
    public BaseRsp<Role> updateRole(@RequestBody @Validated(UpdateStrategy.class) RoleAo roleAo) {
        return BaseRspUtil.data(this.roleService.updateRole(roleAo));
    }
}