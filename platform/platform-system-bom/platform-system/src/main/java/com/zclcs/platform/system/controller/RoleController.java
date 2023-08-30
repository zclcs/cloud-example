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
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author zclcs
 * @since 2023-01-10 10:39:28.842
 */
@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 角色查询（分页）
     * 权限: role:view
     *
     * @see RoleService#findRolePage(BasePageAo, RoleVo)
     */
    @GetMapping
    @SaCheckPermission("role:view")
    public BaseRsp<BasePage<RoleVo>> findRolePage(@Validated BasePageAo basePageAo, @Validated RoleVo roleVo) {
        BasePage<RoleVo> page = this.roleService.findRolePage(basePageAo, roleVo);
        return RspUtil.data(page);
    }

    /**
     * 角色查询（集合）
     * 权限: role:view
     *
     * @see RoleService#findRoleList(RoleVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("role:view")
    public BaseRsp<List<RoleVo>> findRoleList(@Validated RoleVo roleVo) {
        List<RoleVo> list = this.roleService.findRoleList(roleVo);
        return RspUtil.data(list);
    }

    /**
     * 角色查询（单个）
     * 权限: role:view
     *
     * @see RoleService#findRole(RoleVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("role:view")
    public BaseRsp<RoleVo> findRole(@Validated RoleVo roleVo) {
        RoleVo role = this.roleService.findRole(roleVo);
        return RspUtil.data(role);
    }

    /**
     * 根据角色id查询角色
     * 权限: 仅限内部调用
     *
     * @param roleId 角色id
     * @return 角色
     */
    @GetMapping(value = "/findByRoleId/{roleId}")
    @Inner
    public RoleCacheBean findByRoleId(@PathVariable Long roleId) {
        return RoleCacheBean.convertToRoleCacheBean(this.roleService.lambdaQuery().eq(Role::getRoleId, roleId).one());
    }

    /**
     * 角色前端下拉框
     * 权限: user:view 或者 role:view
     *
     * @return 角色集合
     */
    @GetMapping("/options")
    @SaCheckPermission(value = {"user:view", "role:view"}, mode = SaMode.OR)
    public BaseRsp<List<RoleVo>> roles() {
        List<RoleVo> systemRoleList = roleService.findRoleList(RoleVo.builder().build());
        return RspUtil.data(systemRoleList);
    }

    /**
     * 检查角色名
     * 权限: role:add 或者 role:update
     *
     * @param roleId   角色id
     * @param roleName 角色名称
     * @return 是否重复
     */
    @GetMapping("/checkRoleName")
    @SaCheckPermission(value = {"role:add", "role:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkRoleName(@RequestParam(required = false) Long roleId,
                                         @NotBlank(message = "{required}") @RequestParam String roleName) {
        roleService.validateRoleName(roleName, roleId);
        return RspUtil.message();
    }

    /**
     * 检查角色编码
     * 权限: role:add 或者 role:update
     *
     * @param roleId   角色id
     * @param roleCode 角色编码
     * @return 是否重复
     */
    @GetMapping("/checkRoleCode")
    @SaCheckPermission(value = {"role:add", "role:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkRoleCode(@RequestParam(required = false) Long roleId,
                                         @NotBlank(message = "{required}") @RequestParam String roleCode) {
        roleService.validateRoleCode(roleCode, roleId);
        return RspUtil.message();
    }

    /**
     * 新增角色
     * 权限: role:add
     *
     * @see RoleService#createRole(RoleAo)
     */
    @PostMapping
    @SaCheckPermission("role:add")
    @ControllerEndpoint(operation = "新增角色")
    public BaseRsp<Role> addRole(@RequestBody @Validated RoleAo roleAo) {
        return RspUtil.data(this.roleService.createRole(roleAo));
    }

    /**
     * 删除角色
     * 权限: role:delete
     *
     * @param roleIds 角色id集合
     * @see RoleService#deleteRole(List)
     */
    @DeleteMapping("/{roleIds}")
    @SaCheckPermission("role:delete")
    @ControllerEndpoint(operation = "删除角色")
    public BaseRsp<String> deleteRole(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        List<Long> ids = Arrays.stream(roleIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.roleService.deleteRole(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改角色
     * 权限: role:update
     *
     * @see RoleService#updateRole(RoleAo)
     */
    @PutMapping
    @SaCheckPermission("role:update")
    @ControllerEndpoint(operation = "修改角色")
    public BaseRsp<Role> updateRole(@RequestBody @Validated(UpdateStrategy.class) RoleAo roleAo) {
        return RspUtil.data(this.roleService.updateRole(roleAo));
    }
}