package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色菜单关联 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:21.568
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleMenuVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     * 默认值：
     */
    private Long roleId;

    /**
     * 菜单id
     * 默认值：
     */
    private Long menuId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 菜单名称
     */
    private String menuName;


}