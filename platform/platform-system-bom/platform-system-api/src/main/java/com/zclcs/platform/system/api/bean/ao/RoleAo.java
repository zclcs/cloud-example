package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
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
 * @since 2023-09-01 19:53:33.519
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
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long roleId;

    /**
     * 角色编码（唯一值）
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String roleCode;

    /**
     * 角色名称
     * 默认值：
     */
    @Size(max = 10, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String roleName;

    /**
     * 角色描述
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String remark;

    /**
     * 菜单id集合
     */
    @NotNull(message = "{required}")
    private List<Long> menuIds;


}