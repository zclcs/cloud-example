package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_user")
@Schema(title = "User对象", description = "用户")
public class User extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField("real_name")
    private String realName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 联系电话
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 状态 @@system_user.status
     */
    @TableField("status")
    private String status;

    /**
     * 最近访问时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 性别 @@system_user.gender
     */
    @TableField("gender")
    private String gender;

    /**
     * 是否开启tab @@yes_no
     */
    @TableField("is_tab")
    private String isTab;

    /**
     * 主题
     */
    @TableField("theme")
    private String theme;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 描述
     */
    @TableField("description")
    private String description;


}