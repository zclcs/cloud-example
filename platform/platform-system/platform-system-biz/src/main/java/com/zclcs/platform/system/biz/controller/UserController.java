package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.User;
import com.zclcs.platform.system.api.entity.ao.UserAo;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import com.zclcs.platform.system.biz.service.UserService;
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
 * 用户 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Slf4j
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "用户")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "用户查询（分页）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<BasePage<UserVo>> findUserPage(@Validated BasePageAo basePageAo, @Validated UserVo userVo) {
        BasePage<UserVo> page = this.userService.findUserPage(basePageAo, userVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "用户查询（集合）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<List<UserVo>> findUserList(@Validated UserVo userVo) {
        List<UserVo> list = this.userService.findUserList(userVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "用户查询（单个）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<UserVo> findUser(@Validated UserVo userVo) {
        UserVo user = this.userService.findUser(userVo);
        return BaseRspUtil.data(user);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    @ControllerEndpoint(operation = "新增用户")
    @Operation(summary = "新增用户")
    public BaseRsp<User> addUser(@RequestBody @Validated UserAo userAo) {
        return BaseRspUtil.data(this.userService.createUser(userAo));
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAuthority('user:delete')")
    @ControllerEndpoint(operation = "删除用户")
    @Operation(summary = "删除用户")
    @Parameters({
            @Parameter(name = "userIds", description = "用户id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteUser(@NotBlank(message = "{required}") @PathVariable String userIds) {
        List<Long> ids = Arrays.stream(userIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.userService.deleteUser(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    @ControllerEndpoint(operation = "修改用户")
    @Operation(summary = "修改用户")
    public BaseRsp<User> updateUser(@RequestBody @Validated(UpdateStrategy.class) UserAo userAo) {
        return BaseRspUtil.data(this.userService.updateUser(userAo));
    }
}