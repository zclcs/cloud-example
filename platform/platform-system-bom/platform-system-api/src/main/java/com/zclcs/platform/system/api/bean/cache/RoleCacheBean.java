package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.platform.system.api.bean.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色缓存
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleCacheBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码（唯一值）
     */
    private String roleCode;

    /**
     * 角色描述
     */
    private String remark;

    public static RoleCacheBean convertToRoleCacheBean(Role item) {
        if (item == null) {
            return null;
        }
        RoleCacheBean result = new RoleCacheBean();
        result.setRoleId(item.getRoleId());
        result.setRoleName(item.getRoleName());
        result.setRoleCode(item.getRoleCode());
        result.setRemark(item.getRemark());
        return result;
    }


}