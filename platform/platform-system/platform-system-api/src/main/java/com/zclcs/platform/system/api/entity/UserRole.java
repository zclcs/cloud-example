package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.common.datasource.starter.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户角色关联 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:39:38.682
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_user_role")
@Schema(name = "UserRole对象", description = "用户角色关联")
public class UserRole extends BaseEntity {

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;


}