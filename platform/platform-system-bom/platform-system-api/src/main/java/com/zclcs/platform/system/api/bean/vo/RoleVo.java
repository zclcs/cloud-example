package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:33.519
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     * 默认值：
     */
    private Long roleId;

    /**
     * 角色编码（唯一值）
     * 默认值：
     */
    private String roleCode;

    /**
     * 角色名称
     * 默认值：
     */
    private String roleName;

    /**
     * 角色描述
     * 默认值：
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