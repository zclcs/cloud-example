package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.platform.system.api.entity.UserRole;

import java.util.List;

/**
 * 用户角色关联 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:38.682
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 删除角色用户管理关系
     *
     * @param roleIds 角色id数组
     */
    void deleteUserRolesByRoleId(List<Long> roleIds);

    /**
     * 删除角色用户管理关系
     *
     * @param userIds 用户id数组
     */
    void deleteUserRolesByUserId(List<Long> userIds);

    /**
     * 通过角色id查找对应的用户id
     *
     * @param roleIds 角色id
     * @return 用户id集
     */
    List<Long> findUserIdsByRoleId(List<Long> roleIds);

}
