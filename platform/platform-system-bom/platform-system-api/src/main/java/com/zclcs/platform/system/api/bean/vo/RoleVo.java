package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
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
public class RoleVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 角色菜单集合
     */
    private List<RoleMenuVo> roleMenuVos;

    /**
     * 角色菜单编号集合字符串
     */
    private String menuIdString;

    /**
     * 角色菜单id集合
     */
    private List<Long> menuIds;

    /**
     * 角色用户账号集合
     */
    private List<String> usernames;


}