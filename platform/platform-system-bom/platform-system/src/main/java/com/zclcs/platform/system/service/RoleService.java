package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.RoleAo;
import com.zclcs.platform.system.api.bean.entity.Role;
import com.zclcs.platform.system.api.bean.vo.RoleVo;

import java.util.List;

/**
 * 角色 Service接口
 *
 * @author zclcs
 * @since 2023-09-01 19:53:33.519
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param roleVo     {@link RoleVo}
     * @return {@link RoleVo}
     */
    BasePage<RoleVo> findRolePage(BasePageAo basePageAo, RoleVo roleVo);

    /**
     * 查询（所有）
     *
     * @param roleVo {@link RoleVo}
     * @return {@link RoleVo}
     */
    List<RoleVo> findRoleList(RoleVo roleVo);

    /**
     * 查询（单个）
     *
     * @param roleVo {@link RoleVo}
     * @return {@link RoleVo}
     */
    RoleVo findRole(RoleVo roleVo);

    /**
     * 统计
     *
     * @param roleVo {@link RoleVo}
     * @return 统计值
     */
    Long countRole(RoleVo roleVo);

    /**
     * 新增
     *
     * @param roleAo {@link RoleAo}
     * @return {@link Role}
     */
    Role createRole(RoleAo roleAo);

    /**
     * 修改
     *
     * @param roleAo {@link RoleAo}
     * @return {@link Role}
     */
    Role updateRole(RoleAo roleAo);

    /**
     * 删除
     *
     * @param ids 表id集合
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
