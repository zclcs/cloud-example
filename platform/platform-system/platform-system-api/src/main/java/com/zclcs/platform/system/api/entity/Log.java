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

import java.math.BigDecimal;

/**
 * 用户操作日志 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_log")
@Schema(name = "Log对象", description = "用户操作日志")
public class Log extends BaseEntity {

    /**
     * 日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    @TableField("username")
    private String username;

    /**
     * 操作内容
     */
    @TableField("operation")
    private String operation;

    /**
     * 耗时
     */
    @TableField("time")
    private BigDecimal time;

    /**
     * 操作方法
     */
    @TableField("method")
    private String method;

    /**
     * 方法参数
     */
    @TableField("params")
    private String params;

    /**
     * 操作者ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 操作地点
     */
    @TableField("location")
    private String location;


}