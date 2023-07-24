package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.crypto.digest.BCrypt;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.sa.token.utils.LoginHelper;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.ao.UserAo;
import com.zclcs.platform.system.api.bean.cache.UserCacheBean;
import com.zclcs.platform.system.api.bean.entity.User;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.api.bean.vo.UserVo;
import com.zclcs.platform.system.service.UserService;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
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
    @SaCheckPermission("user:view")
    @Operation(summary = "用户查询（分页）")
    public BaseRsp<BasePage<UserVo>> findUserPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated UserVo userVo) {
        BasePage<UserVo> page = this.userService.findUserPage(basePageAo, userVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @SaCheckPermission("user:view")
    @Operation(summary = "用户查询（集合）")
    public BaseRsp<List<UserVo>> findUserList(@ParameterObject @Validated UserVo userVo) {
        List<UserVo> list = this.userService.findUserList(userVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @SaCheckPermission("user:view")
    @Operation(summary = "用户查询（单个）")
    public BaseRsp<UserVo> findUser(@ParameterObject @Validated UserVo userVo) {
        UserVo user = this.userService.findUser(userVo);
        return RspUtil.data(user);
    }

    @GetMapping("/findByUsername/{username}")
    @Operation(summary = "根据用户名查询用户信息")
    @Inner
    public UserCacheBean findByUsername(@PathVariable String username) {
        return UserCacheBean.convertToUserCacheBean(userService.lambdaQuery().eq(User::getUsername, username).one());
    }

    @GetMapping("/findByMobile/{mobile}")
    @Operation(summary = "根据手机号查询用户信息")
    @Inner
    public String findByMobile(@PathVariable String mobile) {
        return userService.lambdaQuery().eq(User::getMobile, mobile).one().getUsername();
    }

    @GetMapping("/findUserInfo")
    @Operation(summary = "用户信息")
    public BaseRsp<UserVo> findUserInfo() {
        UserVo userDetail = SystemCacheUtil.getUserByUsername(LoginHelper.getUsername());
        if (userDetail == null) {
            RspUtil.message("获取当前用户信息失败");
        }
        return RspUtil.data(userDetail);
    }

    @GetMapping("/permissions")
    @Operation(summary = "权限")
    public BaseRsp<List<String>> findUserPermissions() {
        List<String> userPermissions = this.userService.findUserPermissions(LoginHelper.getUsername());
        return RspUtil.data(userPermissions);
    }

    @GetMapping("/routers")
    @Operation(summary = "用户路由")
    public BaseRsp<List<VueRouter<MenuVo>>> getUserRouters() {
        List<VueRouter<MenuVo>> userRouters = this.userService.findUserRouters(LoginHelper.getUsername());
        return RspUtil.data(userRouters);
    }

    @GetMapping("/options")
    @SaCheckPermission("user:view")
    @Operation(summary = "下拉树")
    public BaseRsp<List<UserVo>> userList(@ParameterObject @Validated UserVo userVo) {
        List<UserVo> userDetailPage = userService.findUserList(userVo);
        return RspUtil.data(userDetailPage);
    }

    @GetMapping("/checkUsername")
    @SaCheckPermission(value = {"user:add", "user:update"}, mode = SaMode.OR)
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
    @SaCheckPermission(value = {"user:add", "user:update"}, mode = SaMode.OR)
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
    @SaCheckPermission("user:add")
    @ControllerEndpoint(operation = "新增用户")
    @Operation(summary = "新增用户")
    public BaseRsp<User> addUser(@RequestBody @Validated UserAo userAo) {
        return RspUtil.data(this.userService.createUser(userAo));
    }

    @DeleteMapping("/{userIds}")
    @SaCheckPermission("user:delete")
    @ControllerEndpoint(operation = "删除用户")
    @Operation(summary = "删除用户")
    @Parameters({
            @Parameter(name = "userIds", description = "用户id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteUser(@NotBlank(message = "{required}") @PathVariable String userIds) {
        List<Long> ids = Arrays.stream(userIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.userService.deleteUser(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @SaCheckPermission("user:update")
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
        String currentUsername = LoginHelper.getUsername();
        UserCacheBean userCache = SystemCacheUtil.getUserCache(currentUsername);
        return RspUtil.data(userCache != null && !BCrypt.checkpw(password, userCache.getPassword()));
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

    @GetMapping("/checkPassword/{username}/{password}")
    @SaCheckPermission("user:updatePassword")
    @Operation(summary = "检查用户密码")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.PATH),
            @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<Boolean> checkPassword(@NotBlank(message = "{required}") @PathVariable String username,
                                          @NotBlank(message = "{required}") @PathVariable String password) {
        UserCacheBean userCache = SystemCacheUtil.getUserCache(username);
        return RspUtil.data(userCache != null && !BCrypt.checkpw(password, userCache.getPassword()));
    }

    @PutMapping("/updatePassword/{username}/{password}")
    @SaCheckPermission("user:updatePassword")
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
    @SaCheckPermission("user:updateStatus")
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
    @SaCheckPermission("user:resetPassword")
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