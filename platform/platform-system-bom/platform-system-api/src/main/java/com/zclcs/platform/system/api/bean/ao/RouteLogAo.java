package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 网关转发日志 Ao
 *
 * @author zclcs
 * @since 2023-09-01 20:09:35.391
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RouteLogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关转发日志id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long routeId;

    /**
     * 请求ip
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String routeIp;

    /**
     * 请求uri
     * 默认值：
     */
    @Size(max = 500, message = "{noMoreThan}")
    private String requestUri;

    /**
     * 目标uri
     * 默认值：
     */
    @Size(max = 500, message = "{noMoreThan}")
    private String targetUri;

    /**
     * 请求方法
     * 默认值：
     */
    @Size(max = 10, message = "{noMoreThan}")
    private String requestMethod;

    /**
     * 请求时间
     * 默认值：CURRENT_TIMESTAMP
     */
    @NotNull(message = "{required}")
    private LocalDateTime requestTime;

    /**
     * 目标服务
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String targetServer;

    /**
     * 响应码
     * 默认值：
     */
    @Size(max = 10, message = "{noMoreThan}")
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
    @Size(max = 255, message = "{noMoreThan}")
    private String location;


}