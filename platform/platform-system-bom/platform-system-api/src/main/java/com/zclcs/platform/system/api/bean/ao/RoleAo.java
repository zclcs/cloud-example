package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
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
public class RoleAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long roleId;

    /**
     * 角色名称
     */
    @Size(max = 10, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String roleName;

    /**
     * 角色编码
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String roleCode;

    /**
     * 角色描述
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String remark;

    /**
     * 菜单id集合
     */
    @NotNull(message = "{required}")
    private List<Long> menuIds;


}