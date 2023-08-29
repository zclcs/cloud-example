package com.zclcs.platform.system.api.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
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
@Table("system_route_log")
@TableName("system_route_log")
public class RouteLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关转发日志id
     */
    @TableId(value = "route_id", type = IdType.AUTO)
    @Id(value = "route_id", keyType = KeyType.Auto)
    private Long routeId;

    /**
     * 请求ip
     */
    @TableField("route_ip")
    @Column("route_id")
    private String routeIp;

    /**
     * 请求uri
     */
    @TableField("request_uri")
    @Column("request_uri")
    private String requestUri;

    /**
     * 目标uri
     */
    @TableField("target_uri")
    @Column("target_uri")
    private String targetUri;

    /**
     * 请求方法
     */
    @TableField("request_method")
    @Column("request_method")
    private String requestMethod;

    /**
     * 请求时间
     */
    @TableField("request_time")
    @Column("request_time")
    private LocalDateTime requestTime;

    /**
     * 目标服务
     */
    @TableField("target_server")
    @Column("target_server")
    private String targetServer;

    /**
     * 响应code
     */
    @TableField("code")
    @Column("code")
    private String code;

    /**
     * 响应时间
     */
    @TableField("time")
    @Column("time")
    private Long time;

    /**
     * ip对应地址
     */
    @TableField("location")
    @Column("location")
    private String location;


}