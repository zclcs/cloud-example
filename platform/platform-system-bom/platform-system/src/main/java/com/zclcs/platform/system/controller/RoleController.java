package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.ao.RoleAo;
import com.zclcs.platform.system.api.bean.cache.RoleCacheBean;
import com.zclcs.platform.system.api.bean.entity.Role;
import com.zclcs.platform.system.api.bean.vo.RoleVo;
import com.zclcs.platform.system.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name = "角色")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @SaCheckPermission("role:view")
    @Operation(summary = "角色查询（分页）")
    public BaseRsp<BasePage<RoleVo>> findRolePage(@Validated BasePageAo basePageAo, @Validated RoleVo roleVo) {
        BasePage<RoleVo> page = this.roleService.findRolePage(basePageAo, roleVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @SaCheckPermission("role:view")
    @Operation(summary = "角色查询（集合）")
    public BaseRsp<List<RoleVo>> findRoleList(@Validated RoleVo roleVo) {
        List<RoleVo> list = this.roleService.findRoleList(roleVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @SaCheckPermission("role:view")
    @Operation(summary = "角色查询（单个）")
    public BaseRsp<RoleVo> findRole(@Validated RoleVo roleVo) {
        RoleVo role = this.roleService.findRole(roleVo);
        return RspUtil.data(role);
    }

    @GetMapping(value = "/findByRoleId/{roleId}")
    @Operation(summary = "根据角色id查询角色")
    @Inner
    public RoleCacheBean findByRoleId(@PathVariable Long roleId) {
        return RoleCacheBean.convertToRoleCacheBean(this.roleService.lambdaQuery().eq(Role::getRoleId, roleId).one());
    }

    @GetMapping("/options")
    @SaCheckPermission(value = {"user:view", "role:view"}, mode = SaMode.OR)
    @Operation(summary = "前端下拉框")
    public BaseRsp<List<RoleVo>> roles() {
        List<RoleVo> systemRoleList = roleService.findRoleList(RoleVo.builder().build());
        return RspUtil.data(systemRoleList);
    }

    @GetMapping("/checkRoleName")
    @Operation(summary = "检查角色名")
    @Parameters({
            @Parameter(name = "roleId", description = "角色id", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "roleName", description = "角色名", required = true, in = ParameterIn.QUERY)
    })
    @SaCheckPermission(value = {"role:add", "role:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkRoleName(@RequestParam(required = false) Long roleId,
                                         @NotBlank(message = "{required}") @RequestParam String roleName) {
        roleService.validateRoleName(roleName, roleId);
        return RspUtil.message();
    }

    @GetMapping("/checkRoleCode")
    @Operation(summary = "检查角色编码")
    @Parameters({
            @Parameter(name = "roleId", description = "角色id", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "roleCode", description = "角色编码", required = true, in = ParameterIn.QUERY)
    })
    @SaCheckPermission(value = {"role:add", "role:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkRoleCode(@RequestParam(required = false) Long roleId,
                                         @NotBlank(message = "{required}") @RequestParam String roleCode) {
        roleService.validateRoleCode(roleCode, roleId);
        return RspUtil.message();
    }

    @PostMapping
    @SaCheckPermission("role:add")
    @ControllerEndpoint(operation = "新增角色")
    @Operation(summary = "新增角色")
    public BaseRsp<Role> addRole(@RequestBody @Validated RoleAo roleAo) {
        return RspUtil.data(this.roleService.createRole(roleAo));
    }

    @DeleteMapping("/{roleIds}")
    @SaCheckPermission("role:delete")
    @ControllerEndpoint(operation = "删除角色")
    @Operation(summary = "删除角色")
    @Parameters({
            @Parameter(name = "roleIds", description = "角色id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRole(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        List<Long> ids = Arrays.stream(roleIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.roleService.deleteRole(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @SaCheckPermission("role:update")
    @ControllerEndpoint(operation = "修改角色")
    @Operation(summary = "修改角色")
    public BaseRsp<Role> updateRole(@RequestBody @Validated(UpdateStrategy.class) RoleAo roleAo) {
        return RspUtil.data(this.roleService.updateRole(roleAo));
    }
}