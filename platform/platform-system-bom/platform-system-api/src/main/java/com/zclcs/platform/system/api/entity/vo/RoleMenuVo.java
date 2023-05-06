package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色菜单关联 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:23.376
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "RoleMenuVo对象", description = "角色菜单关联")
public class RoleMenuVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "菜单id")
    private Long menuId;

    @Schema(description = "菜单名称")
    private String menuName;


}