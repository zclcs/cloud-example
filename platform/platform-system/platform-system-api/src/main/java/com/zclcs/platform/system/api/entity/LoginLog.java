package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.common.datasource.starter.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 登录日志 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:39:57.150
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_login_log")
@Schema(name = "LoginLog对象", description = "登录日志")
public class LoginLog extends BaseEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;

    /**
     * 登录地点
     */
    @TableField("location")
    private String location;

    /**
     * ip地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 操作系统
     */
    @TableField("system")
    private String system;

    /**
     * 浏览器
     */
    @TableField("browser")
    private String browser;


}