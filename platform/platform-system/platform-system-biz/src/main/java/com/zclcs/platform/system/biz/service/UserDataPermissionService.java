package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.platform.system.api.entity.UserDataPermission;

import java.util.List;

/**
 * 用户数据权限关联 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
public interface UserDataPermissionService extends IService<UserDataPermission> {

    /**
     * 通过部门ID删除关联关系
     *
     * @param deptIds 部门id
     */
    void deleteByDeptIds(List<Long> deptIds);

    /**
     * 通过用户ID删除关联关系
     *
     * @param userIds 用户id
     */
    void deleteByUserIds(List<Long> userIds);

    /**
     * 通过用户ID查找关联关系
     *
     * @param userId 用户id
     * @return 关联关系
     */
    List<Long> findByUserId(Long userId);

}
