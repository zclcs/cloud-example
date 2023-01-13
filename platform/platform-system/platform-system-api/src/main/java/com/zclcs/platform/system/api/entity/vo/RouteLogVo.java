package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 网关转发日志 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:40:09.958
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "RouteLogVo对象", description = "网关转发日志")
public class RouteLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "网关转发日志id")
    private Long routeId;

    @Schema(description = "请求ip")
    private String routeIp;

    @Schema(description = "请求uri")
    private String requestUri;

    @Schema(description = "目标uri")
    private String targetUri;

    @Schema(description = "请求方法")
    private String requestMethod;

    @Schema(description = "目标服务")
    private String targetServer;

    @Schema(description = "ip对应地址")
    private String location;


}