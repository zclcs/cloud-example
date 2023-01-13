package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色菜单关联 Ao
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
@Schema(name = "RoleMenuAo对象", description = "角色菜单关联")
public class RoleMenuAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "角色编号")
    private Long roleId;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "菜单编号")
    private Long menuId;


}