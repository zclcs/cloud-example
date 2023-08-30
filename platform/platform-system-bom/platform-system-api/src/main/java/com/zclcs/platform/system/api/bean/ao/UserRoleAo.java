package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户角色关联 Ao
 *
 * @author zclcs
 * @since 2023-01-10 10:39:38.682
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRoleAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long userId;

    /**
     * 角色id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long roleId;


}