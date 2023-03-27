package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.User;
import com.zclcs.platform.system.api.entity.ao.UserAo;
import com.zclcs.platform.system.api.entity.router.VueRouter;
import com.zclcs.platform.system.api.entity.vo.MenuVo;
import com.zclcs.platform.system.api.entity.vo.UserVo;

import java.util.List;

/**
 * 用户 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
public interface UserService extends IService<User> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param userVo     userVo
     * @return BasePage<UserVo>
     */
    BasePage<UserVo> findUserPage(BasePageAo basePageAo, UserVo userVo);

    /**
     * 查询（所有）
     *
     * @param userVo userVo
     * @return List<UserVo>
     */
    List<UserVo> findUserList(UserVo userVo);

    /**
     * 查询（单个）
     *
     * @param userVo userVo
     * @return UserVo
     */
    UserVo findUser(UserVo userVo);

    /**
     * 统计
     *
     * @param userVo userVo
     * @return UserVo
     */
    Integer countUser(UserVo userVo);

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    UserVo findByName(String username);

    /**
     * 通过手机号查找用户
     *
     * @param mobile 手机号
     * @return 用户
     */
    UserVo findByMobile(String mobile);

    /**
     * 获取用户路由
     *
     * @param username 用户名
     * @return 用户路由
     */
    List<VueRouter<MenuVo>> findUserRouters(String username);

    /**
     * 获取用户权限
     *
     * @param username 用户名
     * @return 用户权限
     */
    List<String> findUserPermissions(String username);

    /**
     * 新增
     *
     * @param userAo userAo
     * @return User
     */
    User createUser(UserAo userAo);

    /**
     * 修改
     *
     * @param userAo userAo
     * @return User
     */
    User updateUser(UserAo userAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteUser(List<Long> ids);

    /**
     * 更新用户登录时间
     *
     * @param username username
     */
    void updateLoginTime(String username);

    /**
     * 更新用户密码
     *
     * @param username 用户名 为空则修改当前用户
     * @param password 新密码
     */
    void updatePassword(String username, String password);

    /**
     * 更新用户状态
     *
     * @param username 用户名 为空则修改当前用户
     * @param status   状态 为空则修改为禁用状态
     */
    void updateStatus(String username, String status);

    /**
     * 重置密码
     *
     * @param usernames 用户集合
     */
    void resetPassword(List<String> usernames);

    /**
     * 验证用户名
     *
     * @param username 用户名
     * @param userId   用户id
     */
    void validateUsername(String username, Long userId);

    /**
     * 验证手机号
     *
     * @param mobile 手机号
     * @param userId 用户id
     */
    void validateMobile(String mobile, Long userId);

}
