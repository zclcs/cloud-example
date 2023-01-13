package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户数据权限关联 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserDataPermissionAo对象", description = "用户数据权限关联")
public class UserDataPermissionAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "用户编号")
    private Long userId;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "部门编号")
    private Long deptId;


}