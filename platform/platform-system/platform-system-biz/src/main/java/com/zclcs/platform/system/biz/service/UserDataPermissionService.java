package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.UserDataPermission;
import com.zclcs.platform.system.api.entity.ao.UserDataPermissionAo;
import com.zclcs.platform.system.api.entity.vo.UserDataPermissionVo;

import java.util.List;

/**
 * 用户数据权限关联 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
public interface UserDataPermissionService extends IService<UserDataPermission> {

    /**
     * 查询（分页）
     *
     * @param basePageAo           basePageAo
     * @param userDataPermissionVo userDataPermissionVo
     * @return BasePage<UserDataPermissionVo>
     */
    BasePage<UserDataPermissionVo> findUserDataPermissionPage(BasePageAo basePageAo, UserDataPermissionVo userDataPermissionVo);

    /**
     * 查询（所有）
     *
     * @param userDataPermissionVo userDataPermissionVo
     * @return List<UserDataPermissionVo>
     */
    List<UserDataPermissionVo> findUserDataPermissionList(UserDataPermissionVo userDataPermissionVo);

    /**
     * 查询（单个）
     *
     * @param userDataPermissionVo userDataPermissionVo
     * @return UserDataPermissionVo
     */
    UserDataPermissionVo findUserDataPermission(UserDataPermissionVo userDataPermissionVo);

    /**
     * 统计
     *
     * @param userDataPermissionVo userDataPermissionVo
     * @return UserDataPermissionVo
     */
    Integer countUserDataPermission(UserDataPermissionVo userDataPermissionVo);

    /**
     * 新增
     *
     * @param userDataPermissionAo userDataPermissionAo
     * @return UserDataPermission
     */
    UserDataPermission createUserDataPermission(UserDataPermissionAo userDataPermissionAo);

    /**
     * 修改
     *
     * @param userDataPermissionAo userDataPermissionAo
     * @return UserDataPermission
     */
    UserDataPermission updateUserDataPermission(UserDataPermissionAo userDataPermissionAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteUserDataPermission(List<Long> ids);

}
