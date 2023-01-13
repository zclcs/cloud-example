package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.common.datasource.starter.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户数据权限关联 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:39:43.325
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_user_data_permission")
@Schema(name = "UserDataPermission对象", description = "用户数据权限关联")
public class UserDataPermission extends BaseEntity {

    /**
     * 用户编号
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 部门编号
     */
    @TableField(value = "dept_id")
    private Long deptId;


}