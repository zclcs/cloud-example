package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
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
 * @date 2023-01-10 10:40:09.958
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RouteLogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关转发日志id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long routeId;

    /**
     * 请求ip
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String routeIp;

    /**
     * 请求uri
     */
    @Size(max = 500, message = "{noMoreThan}")
    private String requestUri;

    /**
     * 目标uri
     */
    @Size(max = 500, message = "{noMoreThan}")
    private String targetUri;

    /**
     * 请求方法
     */
    @Size(max = 10, message = "{noMoreThan}")
    private String requestMethod;

    /**
     * 目标服务
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String targetServer;

    /**
     * 请求时间
     */
    @NotNull(message = "{required}")
    private LocalDateTime requestTime;

    /**
     * 响应code
     */
    @Size(max = 10, message = "{noMoreThan}")
    private String code;

    /**
     * 响应时间
     */
    private Long time;

    /**
     * ip对应地址
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String location;


}