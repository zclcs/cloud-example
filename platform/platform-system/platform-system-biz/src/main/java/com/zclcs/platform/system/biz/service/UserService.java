package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.User;
import com.zclcs.platform.system.api.entity.ao.UserAo;
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

}
