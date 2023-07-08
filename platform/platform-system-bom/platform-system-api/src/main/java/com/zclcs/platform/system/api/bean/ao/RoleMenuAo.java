package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
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
@Schema(title = "RoleMenuAo对象", description = "角色菜单关联")
public class RoleMenuAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "角色id")
    private Long roleId;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "菜单id")
    private Long menuId;


}