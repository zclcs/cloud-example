package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.UserRole;
import com.zclcs.platform.system.api.entity.ao.UserRoleAo;
import com.zclcs.platform.system.api.entity.vo.UserRoleVo;

import java.util.List;

/**
 * 用户角色关联 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:38.682
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param userRoleVo userRoleVo
     * @return BasePage<UserRoleVo>
     */
    BasePage<UserRoleVo> findUserRolePage(BasePageAo basePageAo, UserRoleVo userRoleVo);

    /**
     * 查询（所有）
     *
     * @param userRoleVo userRoleVo
     * @return List<UserRoleVo>
     */
    List<UserRoleVo> findUserRoleList(UserRoleVo userRoleVo);

    /**
     * 查询（单个）
     *
     * @param userRoleVo userRoleVo
     * @return UserRoleVo
     */
    UserRoleVo findUserRole(UserRoleVo userRoleVo);

    /**
     * 统计
     *
     * @param userRoleVo userRoleVo
     * @return UserRoleVo
     */
    Integer countUserRole(UserRoleVo userRoleVo);

    /**
     * 新增
     *
     * @param userRoleAo userRoleAo
     * @return UserRole
     */
    UserRole createUserRole(UserRoleAo userRoleAo);

    /**
     * 修改
     *
     * @param userRoleAo userRoleAo
     * @return UserRole
     */
    UserRole updateUserRole(UserRoleAo userRoleAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteUserRole(List<Long> ids);

}
