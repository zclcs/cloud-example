package com.zclcs.platform.system.api.bean.cache;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色缓存
 *
 * @author zclcs
 * @since 2023-01-10 10:39:28.842
 */
@Data
public class RoleCacheVo implements Serializable {

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


}