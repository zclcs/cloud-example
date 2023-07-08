package com.zclcs.platform.system.api.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色菜单关联 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:39:23.376
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_role_menu")
@Schema(title = "RoleMenu对象", description = "角色菜单关联")
public class RoleMenu extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    private Long menuId;


}