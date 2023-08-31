package com.zclcs.platform.system.api.bean.vo;

import com.mybatisflex.annotation.Column;
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
 * @since 2023-01-10 10:39:23.376
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
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 角色名称
     */
    @Column(ignore = true)
    private String roleName;

    /**
     * 菜单名称
     */
    @Column(ignore = true)
    private String menuName;


}