package com.zclcs.platform.system.api.bean.vo;

import com.mybatisflex.annotation.Column;
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
 * @since 2023-01-10 10:39:28.842
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
    @Column(ignore = true)
    private List<RoleMenuVo> roleMenuVos;

    /**
     * 角色菜单编号集合字符串
     */
    @Column(ignore = true)
    private String menuIdString;

    /**
     * 角色菜单id集合
     */
    @Column(ignore = true)
    private List<Long> menuIds;

    /**
     * 角色用户账号集合
     */
    @Column(ignore = true)
    private List<String> usernames;


}