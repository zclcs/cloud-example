package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.common.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色 Vo
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
@Schema(title = "RoleVo对象", description = "角色")
public class RoleVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色描述")
    private String remark;

    @Schema(description = "角色菜单集合")
    private List<RoleMenuVo> roleMenuVos;

    @Schema(description = "角色菜单编号集合字符串")
    private String menuIdString;

    @Schema(description = "角色菜单id集合")
    private List<Long> menuIds;

    @Schema(description = "角色用户账号集合")
    private List<String> usernames;


}