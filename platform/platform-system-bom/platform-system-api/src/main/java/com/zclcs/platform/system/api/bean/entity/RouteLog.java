package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 网关转发日志 Entity
 *
 * @author zclcs
 * @since 2023-01-10 10:40:09.958
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_route_log")
public class RouteLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关转发日志id
     */
    @Id(value = "route_id", keyType = KeyType.Auto)
    private Long routeId;

    /**
     * 请求ip
     */
    @Column("route_id")
    private String routeIp;

    /**
     * 请求uri
     */
    @Column("request_uri")
    private String requestUri;

    /**
     * 目标uri
     */
    @Column("target_uri")
    private String targetUri;

    /**
     * 请求方法
     */
    @Column("request_method")
    private String requestMethod;

    /**
     * 请求时间
     */
    @Column("request_time")
    private LocalDateTime requestTime;

    /**
     * 目标服务
     */
    @Column("target_server")
    private String targetServer;

    /**
     * 响应code
     */
    @Column("code")
    private String code;

    /**
     * 响应时间
     */
    @Column("time")
    private Long time;

    /**
     * ip对应地址
     */
    @Column("location")
    private String location;


}