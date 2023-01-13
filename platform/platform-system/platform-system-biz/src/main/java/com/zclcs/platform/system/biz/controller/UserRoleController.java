package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.UserRole;
import com.zclcs.platform.system.api.entity.ao.UserRoleAo;
import com.zclcs.platform.system.api.entity.vo.UserRoleVo;
import com.zclcs.platform.system.biz.service.UserRoleService;
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
 * 用户角色关联 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:38.682
 */
@Slf4j
@RestController
@RequestMapping("userRole")
@RequiredArgsConstructor
@Tag(name = "用户角色关联")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @GetMapping
    @Operation(summary = "用户角色关联查询（分页）")
    @PreAuthorize("hasAuthority('userRole:view')")
    public BaseRsp<BasePage<UserRoleVo>> findUserRolePage(@Validated BasePageAo basePageAo, @Validated UserRoleVo userRoleVo) {
        BasePage<UserRoleVo> page = this.userRoleService.findUserRolePage(basePageAo, userRoleVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "用户角色关联查询（集合）")
    @PreAuthorize("hasAuthority('userRole:view')")
    public BaseRsp<List<UserRoleVo>> findUserRoleList(@Validated UserRoleVo userRoleVo) {
        List<UserRoleVo> list = this.userRoleService.findUserRoleList(userRoleVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "用户角色关联查询（单个）")
    @PreAuthorize("hasAuthority('userRole:view')")
    public BaseRsp<UserRoleVo> findUserRole(@Validated UserRoleVo userRoleVo) {
        UserRoleVo userRole = this.userRoleService.findUserRole(userRoleVo);
        return BaseRspUtil.data(userRole);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('userRole:add')")
    @ControllerEndpoint(operation = "新增用户角色关联")
    @Operation(summary = "新增用户角色关联")
    public BaseRsp<UserRole> addUserRole(@RequestBody @Validated UserRoleAo userRoleAo) {
        return BaseRspUtil.data(this.userRoleService.createUserRole(userRoleAo));
    }

    @DeleteMapping("/{userRoleIds}")
    @PreAuthorize("hasAuthority('userRole:delete')")
    @ControllerEndpoint(operation = "删除用户角色关联")
    @Operation(summary = "删除用户角色关联")
    @Parameters({
            @Parameter(name = "userRoleIds", description = "用户角色关联id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteUserRole(@NotBlank(message = "{required}") @PathVariable String userRoleIds) {
        List<Long> ids = Arrays.stream(userRoleIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.userRoleService.deleteUserRole(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('userRole:update')")
    @ControllerEndpoint(operation = "修改用户角色关联")
    @Operation(summary = "修改用户角色关联")
    public BaseRsp<UserRole> updateUserRole(@RequestBody @Validated(UpdateStrategy.class) UserRoleAo userRoleAo) {
        return BaseRspUtil.data(this.userRoleService.updateUserRole(userRoleAo));
    }
}