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

/**
 * 用户数据权限关联 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:55:16.457
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDataPermissionVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     * 默认值：
     */
    private Long userId;

    /**
     * 部门id
     * 默认值：
     */
    private Long deptId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 部门名称
     */
    private String deptName;


}