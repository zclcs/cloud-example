package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.common.security.starter.annotation.Inner;
import com.zclcs.common.security.starter.utils.PasswordUtil;
import com.zclcs.common.security.starter.utils.SecurityUtil;
import com.zclcs.platform.system.api.entity.User;
import com.zclcs.platform.system.api.entity.ao.UserAo;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import com.zclcs.platform.system.biz.service.UserDataPermissionService;
import com.zclcs.platform.system.biz.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {

    private final UserService userService;
    private final UserDataPermissionService userDataPermissionService;

    @GetMapping
    @Operation(summary = "用户查询（分页）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<BasePage<UserVo>> findUserPage(@Validated BasePageAo basePageAo, @Validated UserVo userVo) {
        BasePage<UserVo> page = this.userService.findUserPage(basePageAo, userVo);
        return RspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "用户查询（集合）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<List<UserVo>> findUserList(@Validated UserVo userVo) {
        List<UserVo> list = this.userService.findUserList(userVo);
        return RspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "用户查询（单个）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<UserVo> findUser(@Validated UserVo userVo) {
        UserVo user = this.userService.findUser(userVo);
        return RspUtil.data(user);
    }

    @GetMapping("info")
    @Operation(summary = "用户信息")
    public BaseRsp<UserVo> findUserInfo() {
        UserVo userDetail = userService.findUserDetail(SecurityUtil.getUsername());
        if (userDetail == null) {
            RspUtil.message("获取当前用户信息失败");
        }
        return RspUtil.data(userDetail);
    }

    @GetMapping("info/{username}")
    @Operation(summary = "用户信息")
    @Inner
    public BaseRsp<UserVo> findUserDetail(@PathVariable String username) {
        UserVo userDetail = userService.findUserDetail(username);
        if (userDetail == null) {
            RspUtil.message("用户信息为空");
        }
        return RspUtil.data(userDetail);
    }

    @GetMapping("infoByMobile/{mobile}")
    @Operation(summary = "用户信息")
    @Inner
    public BaseRsp<UserVo> findUserDetailByMobile(@PathVariable String mobile) {
        UserVo userDetail = userService.findUserDetailByMobile(mobile);
        if (userDetail == null) {
            RspUtil.message("用户信息为空");
        }
        return RspUtil.data(userDetail);
    }

    @GetMapping("options")
    @PreAuthorize("hasAuthority('user:view')")
    @Operation(summary = "下拉树")
    public BaseRsp<List<UserVo>> userList(@Validated UserVo selectSystemUserAo) {
        List<UserVo> userDetailPage = userService.findUserList(selectSystemUserAo);
        return RspUtil.data(userDetailPage);
    }

    @GetMapping("check/{userId}/{username}")
    @Operation(summary = "检查用户名")
    @Parameters({
            @Parameter(name = "userId", description = "用户id", required = true, in = ParameterIn.PATH),
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<Boolean> checkUserName(@NotNull(message = "{required}") @PathVariable Long userId,
                                          @NotBlank(message = "{required}") @PathVariable String username) {
        User one = userService.lambdaQuery().eq(User::getUserId, userId).one();
        if (one.getUsername().equals(username)) {
            return RspUtil.data(false);
        }
        return RspUtil.data(this.userService.findByName(username) != null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    @ControllerEndpoint(operation = "新增用户")
    @Operation(summary = "新增用户")
    public BaseRsp<User> addUser(@RequestBody @Validated UserAo userAo) {
        return RspUtil.data(this.userService.createUser(userAo));
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
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    @ControllerEndpoint(operation = "修改用户")
    @Operation(summary = "修改用户")
    public BaseRsp<User> updateUser(@RequestBody @Validated(UpdateStrategy.class) UserAo userAo) {
        return RspUtil.data(this.userService.updateUser(userAo));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('user:view')")
    @Operation(summary = "获取数据权限")
    @Parameters({
            @Parameter(name = "userId", description = "用户id", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<List<Long>> findUserDataPermissions(@NotBlank(message = "{required}") @PathVariable Long userId) {
        List<Long> dataPermissions = this.userDataPermissionService.findByUserId(userId);
        return RspUtil.data(dataPermissions);
    }

    @GetMapping("password/mine/check/{password}")
    @Operation(summary = "检查当前用户密码")
    @Parameters({
            @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<Boolean> checkMyPassword(@NotBlank(message = "{required}") @PathVariable String password) {
        String currentUsername = SecurityUtil.getUsername();
        UserVo user = userService.findByName(currentUsername);
        return RspUtil.data(user != null && PasswordUtil.PASSWORD_ENCODER.matches(password, user.getPassword()));
    }

    @GetMapping("password/{username}/check/{password}")
    @Operation(summary = "检查用户密码")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.PATH),
            @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<Boolean> checkPassword(@NotBlank(message = "{required}") @PathVariable String username,
                                          @NotBlank(message = "{required}") @PathVariable String password) {
        UserVo user = userService.findByName(username);
        return RspUtil.data(user != null && PasswordUtil.PASSWORD_ENCODER.matches(password, user.getPassword()));
    }

    @PutMapping("password/mine")
    @Operation(summary = "修改当前用户密码")
    @Parameters({
            @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.QUERY)
    })
    @ControllerEndpoint(operation = "修改当前用户密码")
    public void updateMyPassword(@NotBlank(message = "{required}") String password) {
        userService.updatePassword(null, password);
    }

    @PutMapping("password")
    @Operation(summary = "修改密码")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.QUERY)
    })
    @ControllerEndpoint(operation = "修改密码")
    public void updatePassword(@NotBlank(message = "{required}") String username,
                               @NotBlank(message = "{required}") String password) {
        userService.updatePassword(username, password);
    }

    @PutMapping("status")
    @Operation(summary = "禁用账号")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.QUERY)
    })
    @ControllerEndpoint(operation = "禁用账号")
    public void updateStatus(@NotBlank(message = "{required}") String username) {
        userService.updateStatus(username, null);
    }

    @PutMapping("password/reset")
    @PreAuthorize("hasAuthority('user:reset')")
    @Operation(summary = "重置用户密码")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.QUERY)
    })
    @ControllerEndpoint(operation = "重置用户密码")
    public void resetPassword(@NotBlank(message = "{required}") String username) {
        List<String> usernameList = new ArrayList<>();
        usernameList.add(username);
        this.userService.resetPassword(usernameList);
    }
}