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
 * 网关转发日志 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:40:09.958
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_route_log")
@Schema(title = "RouteLog对象", description = "网关转发日志")
public class RouteLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关转发日志id
     */
    @TableId(value = "route_id", type = IdType.AUTO)
    private Long routeId;

    /**
     * 请求ip
     */
    @TableField("route_ip")
    private String routeIp;

    /**
     * 请求uri
     */
    @TableField("request_uri")
    private String requestUri;

    /**
     * 目标uri
     */
    @TableField("target_uri")
    private String targetUri;

    /**
     * 请求方法
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求时间
     */
    @TableField("request_time")
    private LocalDateTime requestTime;

    /**
     * 目标服务
     */
    @TableField("target_server")
    private String targetServer;

    /**
     * 响应code
     */
    @TableField("code")
    private String code;

    /**
     * 响应时间
     */
    @TableField("time")
    private Long time;

    /**
     * ip对应地址
     */
    @TableField("location")
    private String location;


}