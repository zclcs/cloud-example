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
import com.zclcs.platform.system.api.entity.router.VueRouter;
import com.zclcs.platform.system.api.entity.vo.MenuVo;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import com.zclcs.platform.system.biz.service.UserService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "用户查询（分页）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<BasePage<UserVo>> findUserPage(@Validated BasePageAo basePageAo, @Validated UserVo userVo) {
        BasePage<UserVo> page = this.userService.findUserPage(basePageAo, userVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @Operation(summary = "用户查询（集合）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<List<UserVo>> findUserList(@Validated UserVo userVo) {
        List<UserVo> list = this.userService.findUserList(userVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @Operation(summary = "用户查询（单个）")
    @PreAuthorize("hasAuthority('user:view')")
    public BaseRsp<UserVo> findUser(@Validated UserVo userVo) {
        UserVo user = this.userService.findUser(userVo);
        return RspUtil.data(user);
    }

    @GetMapping("/findByUsername/{username}")
    @Operation(summary = "根据用户名查询用户信息")
    @Inner
    public BaseRsp<User> findByUsername(@PathVariable String username) {
        User user = userService.lambdaQuery().eq(User::getUsername, username).one();
        return RspUtil.data(user);
    }

    @GetMapping("/findByMobile/{mobile}")
    @Operation(summary = "根据手机号查询用户信息")
    @Inner
    public BaseRsp<String> findByMobile(@PathVariable String mobile) {
        User user = userService.lambdaQuery().eq(User::getMobile, mobile).one();
        return RspUtil.data(user.getUsername());
    }

    @GetMapping("/findUserInfo")
    @Operation(summary = "用户信息")
    public BaseRsp<UserVo> findUserInfo() {
        UserVo userDetail = SystemCacheUtil.getUserByUsername(SecurityUtil.getUsername());
        if (userDetail == null) {
            RspUtil.message("获取当前用户信息失败");
        }
        return RspUtil.data(userDetail);
    }

    @GetMapping("/permissions")
    @Operation(summary = "权限")
    public BaseRsp<List<String>> findUserPermissions() {
        List<String> userPermissions = this.userService.findUserPermissions(SecurityUtil.getUsername());
        return RspUtil.data(userPermissions);
    }

    @GetMapping("/routers")
    @Operation(summary = "用户路由")
    public BaseRsp<List<VueRouter<MenuVo>>> getUserRouters() {
        List<VueRouter<MenuVo>> userRouters = this.userService.findUserRouters(SecurityUtil.getUsername());
        return RspUtil.data(userRouters);
    }

    @GetMapping("/options")
    @PreAuthorize("hasAuthority('user:view')")
    @Operation(summary = "下拉树")
    public BaseRsp<List<UserVo>> userList(@Validated UserVo selectSystemUserAo) {
        List<UserVo> userDetailPage = userService.findUserList(selectSystemUserAo);
        return RspUtil.data(userDetailPage);
    }

    @GetMapping("/checkUsername")
    @Operation(summary = "检查用户名")
    @Parameters({
            @Parameter(name = "userId", description = "用户id", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<Object> checkUserName(@RequestParam(required = false) Long userId,
                                         @NotBlank(message = "{required}") @RequestParam String username) {
        userService.validateUsername(username, userId);
        return RspUtil.message();
    }

    @GetMapping("/checkUserMobile")
    @Operation(summary = "检查用户手机号")
    @Parameters({
            @Parameter(name = "userId", description = "用户id", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "mobile", description = "手机号", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<Object> checkUserMobile(@RequestParam(required = false) Long userId,
                                           @NotBlank(message = "{required}") @RequestParam String mobile) {
        userService.validateMobile(mobile, userId);
        return RspUtil.message();
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

    @GetMapping("/checkMinePassword/{password}")
    @Operation(summary = "检查当前用户密码")
    @Parameters({
            @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<Boolean> checkMyPassword(@NotBlank(message = "{required}") @PathVariable String password) {
        String currentUsername = SecurityUtil.getUsername();
        UserVo user = userService.findByName(currentUsername);
        return RspUtil.data(user != null && PasswordUtil.PASSWORD_ENCODER.matches(password, user.getPassword()));
    }

    @GetMapping("/checkPassword/{username}/{password}")
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

    @PutMapping("/updateMinePassword/{password}")
    @Operation(summary = "修改当前用户密码")
    @Parameters({
            @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.PATH)
    })
    @ControllerEndpoint(operation = "修改当前用户密码")
    public BaseRsp<Object> updateMyPassword(@NotBlank(message = "{required}") @PathVariable String password) {
        userService.updatePassword(null, password);
        return RspUtil.message();
    }

    @PutMapping("/updatePassword/{username}/{password}")
    @Operation(summary = "修改密码")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.PATH),
            @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.PATH)
    })
    @ControllerEndpoint(operation = "修改密码")
    public BaseRsp<Object> updatePassword(@NotBlank(message = "{required}") @PathVariable String username,
                                          @NotBlank(message = "{required}") @PathVariable String password) {
        userService.updatePassword(username, password);
        return RspUtil.message();
    }

    @PutMapping("/updateUserStatus/{username}")
    @Operation(summary = "禁用账号")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.QUERY)
    })
    @ControllerEndpoint(operation = "禁用账号")
    public BaseRsp<Object> updateStatus(@NotBlank(message = "{required}") @PathVariable String username) {
        userService.updateStatus(username, null);
        return RspUtil.message();
    }

    @PutMapping("/resetPassword/{username}")
    @PreAuthorize("hasAuthority('user:reset')")
    @Operation(summary = "重置用户密码")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.QUERY)
    })
    @ControllerEndpoint(operation = "重置用户密码")
    public BaseRsp<Object> resetPassword(@NotBlank(message = "{required}") @PathVariable String username) {
        List<String> usernameList = new ArrayList<>();
        usernameList.add(username);
        this.userService.resetPassword(usernameList);
        return RspUtil.message();
    }
}