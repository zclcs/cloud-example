package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 角色 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:39:28.842
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "RoleAo对象", description = "角色")
public class RoleAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "角色id")
    private Long roleId;

    @Size(max = 10, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleName;

    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleCode;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(description = "角色描述")
    private String remark;

    @Schema(description = "菜单编码集合")
    @NotNull(message = "{required}")
    private List<Long> menuIds;


}