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
import com.zclcs.cloud.lib.sa.token.api.utils.LoginHelper;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.ao.UserAo;
import com.zclcs.platform.system.api.bean.cache.UserCacheBean;
import com.zclcs.platform.system.api.bean.entity.User;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.api.bean.vo.UserVo;
import com.zclcs.platform.system.service.UserService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户查询（分页）
     * 权限: user:view
     *
     * @see UserService#findUserPage(BasePageAo, UserVo)
     */
    @GetMapping
    @SaCheckPermission("user:view")
    public BaseRsp<BasePage<UserVo>> findUserPage(@Validated BasePageAo basePageAo, @Validated UserVo userVo) {
        BasePage<UserVo> page = this.userService.findUserPage(basePageAo, userVo);
        return RspUtil.data(page);
    }

    /**
     * 用户查询（集合）
     * 权限: user:view
     *
     * @see UserService#findUserList(UserVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("user:view")
    public BaseRsp<List<UserVo>> findUserList(@Validated UserVo userVo) {
        List<UserVo> list = this.userService.findUserList(userVo);
        return RspUtil.data(list);
    }

    /**
     * 用户查询（单个）
     * 权限: user:view
     *
     * @see UserService#findUser(UserVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("user:view")
    public BaseRsp<UserVo> findUser(@Validated UserVo userVo) {
        UserVo user = this.userService.findUser(userVo);
        return RspUtil.data(user);
    }

    /**
     * 根据用户名查询用户信息
     * 权限: 仅限内部调用
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/findByUsername/{username}")
    @Inner
    public UserCacheBean findByUsername(@PathVariable String username) {
        return UserCacheBean.convertToUserCacheBean(userService.lambdaQuery().eq(User::getUsername, username).one());
    }

    /**
     * 根据手机号查询用户信息
     * 权限: 仅限内部调用
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @GetMapping("/findByMobile/{mobile}")
    @Inner
    public String findByMobile(@PathVariable String mobile) {
        return userService.lambdaQuery().eq(User::getMobile, mobile).one().getUsername();
    }

    /**
     * 获取用户信息
     * 权限: 用户登录即可获取
     * 包括用户权限、用户数据权限
     *
     * @return 用户信息
     */
    @GetMapping("/findUserInfo")
    public BaseRsp<UserVo> findUserInfo() {
        UserVo userDetail = SystemCacheUtil.getUserByUsername(LoginHelper.getUsername());
        if (userDetail == null) {
            RspUtil.message("获取当前用户信息失败");
        }
        return RspUtil.data(userDetail);
    }

    /**
     * 获取用户权限
     * 权限: 用户登录即可获取
     *
     * @return 用户权限
     */
    @GetMapping("/permissions")
    public BaseRsp<List<String>> findUserPermissions() {
        List<String> userPermissions = this.userService.findUserPermissions(LoginHelper.getUsername());
        return RspUtil.data(userPermissions);
    }

    /**
     * 获取用户权限
     * 权限: 用户登录即可获取
     *
     * @return 用户权限
     */
    @GetMapping("/permissions/test")
    public BaseRsp<Object> test() {
        return RspUtil.message();
    }

    /**
     * 获取用户路由
     * 权限: 用户登录即可获取
     *
     * @return 用户路由
     */
    @GetMapping("/routers")
    public BaseRsp<List<VueRouter<MenuVo>>> getUserRouters() {
        List<VueRouter<MenuVo>> userRouters = this.userService.findUserRouters(LoginHelper.getUsername());
        return RspUtil.data(userRouters);
    }

    /**
     * 用户下拉框
     * 权限: user:view
     *
     * @see UserService#findUserList(UserVo)
     */
    @GetMapping("/options")
    @SaCheckPermission("user:view")
    public BaseRsp<List<UserVo>> userList(@Validated UserVo userVo) {
        List<UserVo> userDetailPage = userService.findUserList(userVo);
        return RspUtil.data(userDetailPage);
    }

    /**
     * 检查用户名
     * 权限: user:add 或者 user:update
     *
     * @param userId   用户id
     * @param username 用户名
     * @return 是否重复
     */
    @GetMapping("/checkUsername")
    @SaCheckPermission(value = {"user:add", "user:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkUserName(@RequestParam(required = false) Long userId,
                                         @NotBlank(message = "{required}") @RequestParam String username) {
        userService.validateUsername(username, userId);
        return RspUtil.message();
    }

    /**
     * 检查用户手机号
     * 权限: user:add 或者 user:update
     *
     * @param userId 用户id
     * @param mobile 用户手机号
     * @return 是否重复
     */
    @GetMapping("/checkUserMobile")
    @SaCheckPermission(value = {"user:add", "user:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkUserMobile(@RequestParam(required = false) Long userId,
                                           @NotBlank(message = "{required}") @RequestParam String mobile) {
        userService.validateMobile(mobile, userId);
        return RspUtil.message();
    }

    /**
     * 新增用户
     * 权限: user:add
     *
     * @see UserService#createUser(UserAo)
     */
    @PostMapping
    @SaCheckPermission("user:add")
    @ControllerEndpoint(operation = "新增用户")
    public BaseRsp<User> addUser(@RequestBody @Validated UserAo userAo) {
        return RspUtil.data(this.userService.createUser(userAo));
    }

    /**
     * 删除用户
     * 权限: user:delete
     *
     * @param userIds 用户id集合(,分隔)
     * @see UserService#deleteUser(List)
     */
    @DeleteMapping("/{userIds}")
    @SaCheckPermission("user:delete")
    @ControllerEndpoint(operation = "删除用户")
    public BaseRsp<String> deleteUser(@NotBlank(message = "{required}") @PathVariable String userIds) {
        List<Long> ids = Arrays.stream(userIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.userService.deleteUser(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 新增用户
     * 权限: user:update
     *
     * @see UserService#deleteUser(List)
     */
    @PutMapping
    @SaCheckPermission("user:update")
    @ControllerEndpoint(operation = "修改用户")
    public BaseRsp<User> updateUser(@RequestBody @Validated(UpdateStrategy.class) UserAo userAo) {
        return RspUtil.data(this.userService.updateUser(userAo));
    }

    /**
     * 检查当前用户密码
     * 权限: 登录即可调用
     *
     * @param password 密码
     * @return 是否正确
     */
    @GetMapping("/checkMinePassword/{password}")
    public BaseRsp<Boolean> checkMyPassword(@NotBlank(message = "{required}") @PathVariable String password) {
        String currentUsername = LoginHelper.getUsername();
        UserCacheBean userCache = SystemCacheUtil.getUserCache(currentUsername);
        return RspUtil.data(userCache != null && !BCrypt.checkpw(password, userCache.getPassword()));
    }

    /**
     * 修改当前用户密码
     * 权限: 登录即可调用
     *
     * @param password 密码
     * @return 是否正确
     */
    @PutMapping("/updateMinePassword/{password}")
    @ControllerEndpoint(operation = "修改当前用户密码")
    public BaseRsp<Object> updateMyPassword(@NotBlank(message = "{required}") @PathVariable String password) {
        userService.updatePassword(null, password);
        return RspUtil.message();
    }

    /**
     * 检查用户密码
     * 权限: user:updatePassword
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否正确
     */
    @GetMapping("/checkPassword/{username}/{password}")
    @SaCheckPermission("user:updatePassword")
    public BaseRsp<Boolean> checkPassword(@NotBlank(message = "{required}") @PathVariable String username,
                                          @NotBlank(message = "{required}") @PathVariable String password) {
        UserCacheBean userCache = SystemCacheUtil.getUserCache(username);
        return RspUtil.data(userCache != null && !BCrypt.checkpw(password, userCache.getPassword()));
    }

    /**
     * 修改用户密码
     * 权限: user:updatePassword
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否正确
     */
    @PutMapping("/updatePassword/{username}/{password}")
    @SaCheckPermission("user:updatePassword")
    @ControllerEndpoint(operation = "修改密码")
    public BaseRsp<Object> updatePassword(@NotBlank(message = "{required}") @PathVariable String username,
                                          @NotBlank(message = "{required}") @PathVariable String password) {
        userService.updatePassword(username, password);
        return RspUtil.message();
    }

    /**
     * 禁用账号
     * 权限: user:updateStatus
     *
     * @param username 用户名
     * @return 是否成功
     */
    @PutMapping("/updateUserStatus/{username}")
    @SaCheckPermission("user:updateStatus")
    @ControllerEndpoint(operation = "禁用账号")
    public BaseRsp<Object> updateStatus(@NotBlank(message = "{required}") @PathVariable String username) {
        userService.updateStatus(username, null);
        return RspUtil.message();
    }

    /**
     * 重置用户密码
     * 权限: user:resetPassword
     *
     * @param username 用户名
     * @return 是否成功
     */
    @PutMapping("/resetPassword/{username}")
    @SaCheckPermission("user:resetPassword")
    @ControllerEndpoint(operation = "重置用户密码")
    public BaseRsp<Object> resetPassword(@NotBlank(message = "{required}") @PathVariable String username) {
        List<String> usernameList = new ArrayList<>();
        usernameList.add(username);
        this.userService.resetPassword(usernameList);
        return RspUtil.message();
    }
}