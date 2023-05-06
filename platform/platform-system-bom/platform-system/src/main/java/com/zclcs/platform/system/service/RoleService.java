package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.Role;
import com.zclcs.platform.system.api.entity.ao.RoleAo;
import com.zclcs.platform.system.api.entity.vo.RoleVo;

import java.util.List;

/**
 * 角色 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param roleVo     roleVo
     * @return BasePage<RoleVo>
     */
    BasePage<RoleVo> findRolePage(BasePageAo basePageAo, RoleVo roleVo);

    /**
     * 查询（所有）
     *
     * @param roleVo roleVo
     * @return List<RoleVo>
     */
    List<RoleVo> findRoleList(RoleVo roleVo);

    /**
     * 查询（单个）
     *
     * @param roleVo roleVo
     * @return RoleVo
     */
    RoleVo findRole(RoleVo roleVo);

    /**
     * 统计
     *
     * @param roleVo roleVo
     * @return RoleVo
     */
    Integer countRole(RoleVo roleVo);

    /**
     * 新增
     *
     * @param roleAo roleAo
     * @return Role
     */
    Role createRole(RoleAo roleAo);

    /**
     * 修改
     *
     * @param roleAo roleAo
     * @return Role
     */
    Role updateRole(RoleAo roleAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteRole(List<Long> ids);

    /**
     * 验证角色编码
     *
     * @param roleCode 角色编码
     * @param roleId   角色id
     */
    void validateRoleCode(String roleCode, Long roleId);

    /**
     * 验证角色名称
     *
     * @param roleName 角色名称
     * @param roleId   角色id
     */
    void validateRoleName(String roleName, Long roleId);

}
