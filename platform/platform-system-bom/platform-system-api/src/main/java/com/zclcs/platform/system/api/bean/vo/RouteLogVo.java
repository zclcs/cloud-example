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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 网关转发日志 Vo
 *
 * @author zclcs
 * @since 2023-09-01 20:09:35.391
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RouteLogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关转发日志id
     * 默认值：
     */
    private Long routeId;

    /**
     * 请求ip
     * 默认值：
     */
    private String routeIp;

    /**
     * 请求uri
     * 默认值：
     */
    private String requestUri;

    /**
     * 目标uri
     * 默认值：
     */
    private String targetUri;

    /**
     * 请求方法
     * 默认值：
     */
    private String requestMethod;

    /**
     * 请求时间
     * 默认值：CURRENT_TIMESTAMP
     */
    private LocalDateTime requestTime;

    /**
     * 目标服务
     * 默认值：
     */
    private String targetServer;

    /**
     * 响应码
     * 默认值：
     */
    private String code;

    /**
     * 响应时间
     * 默认值：0
     */
    private Long time;

    /**
     * ip对应地址
     * 默认值：
     */
    private String location;

    /**
     * 请求时间起
     */
    private LocalDate requestTimeFrom;

    /**
     * 请求时间起终
     */
    private LocalDate requestTimeTo;


}