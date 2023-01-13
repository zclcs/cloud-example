package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.UserDataPermission;
import com.zclcs.platform.system.api.entity.ao.UserDataPermissionAo;
import com.zclcs.platform.system.api.entity.vo.UserDataPermissionVo;
import com.zclcs.platform.system.biz.service.UserDataPermissionService;
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
 * 用户数据权限关联 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
@Slf4j
@RestController
@RequestMapping("userDataPermission")
@RequiredArgsConstructor
@Tag(name = "用户数据权限关联")
public class UserDataPermissionController {

    private final UserDataPermissionService userDataPermissionService;

    @GetMapping
    @Operation(summary = "用户数据权限关联查询（分页）")
    @PreAuthorize("hasAuthority('userDataPermission:view')")
    public BaseRsp<BasePage<UserDataPermissionVo>> findUserDataPermissionPage(@Validated BasePageAo basePageAo, @Validated UserDataPermissionVo userDataPermissionVo) {
        BasePage<UserDataPermissionVo> page = this.userDataPermissionService.findUserDataPermissionPage(basePageAo, userDataPermissionVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "用户数据权限关联查询（集合）")
    @PreAuthorize("hasAuthority('userDataPermission:view')")
    public BaseRsp<List<UserDataPermissionVo>> findUserDataPermissionList(@Validated UserDataPermissionVo userDataPermissionVo) {
        List<UserDataPermissionVo> list = this.userDataPermissionService.findUserDataPermissionList(userDataPermissionVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "用户数据权限关联查询（单个）")
    @PreAuthorize("hasAuthority('userDataPermission:view')")
    public BaseRsp<UserDataPermissionVo> findUserDataPermission(@Validated UserDataPermissionVo userDataPermissionVo) {
        UserDataPermissionVo userDataPermission = this.userDataPermissionService.findUserDataPermission(userDataPermissionVo);
        return BaseRspUtil.data(userDataPermission);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('userDataPermission:add')")
    @ControllerEndpoint(operation = "新增用户数据权限关联")
    @Operation(summary = "新增用户数据权限关联")
    public BaseRsp<UserDataPermission> addUserDataPermission(@RequestBody @Validated UserDataPermissionAo userDataPermissionAo) {
        return BaseRspUtil.data(this.userDataPermissionService.createUserDataPermission(userDataPermissionAo));
    }

    @DeleteMapping("/{userDataPermissionIds}")
    @PreAuthorize("hasAuthority('userDataPermission:delete')")
    @ControllerEndpoint(operation = "删除用户数据权限关联")
    @Operation(summary = "删除用户数据权限关联")
    @Parameters({
            @Parameter(name = "userDataPermissionIds", description = "用户数据权限关联id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteUserDataPermission(@NotBlank(message = "{required}") @PathVariable String userDataPermissionIds) {
        List<Long> ids = Arrays.stream(userDataPermissionIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.userDataPermissionService.deleteUserDataPermission(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('userDataPermission:update')")
    @ControllerEndpoint(operation = "修改用户数据权限关联")
    @Operation(summary = "修改用户数据权限关联")
    public BaseRsp<UserDataPermission> updateUserDataPermission(@RequestBody @Validated(UpdateStrategy.class) UserDataPermissionAo userDataPermissionAo) {
        return BaseRspUtil.data(this.userDataPermissionService.updateUserDataPermission(userDataPermissionAo));
    }
}