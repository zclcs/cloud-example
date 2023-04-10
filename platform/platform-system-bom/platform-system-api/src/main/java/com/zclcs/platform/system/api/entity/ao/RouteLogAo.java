package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 网关转发日志 Ao
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
@Schema(title = "RouteLogAo对象", description = "网关转发日志")
public class RouteLogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "网关转发日志id")
    private Long routeId;

    @Size(max = 200, message = "{noMoreThan}")
    @Schema(description = "请求ip")
    private String routeIp;

    @Size(max = 500, message = "{noMoreThan}")
    @Schema(description = "请求uri")
    private String requestUri;

    @Size(max = 500, message = "{noMoreThan}")
    @Schema(description = "目标uri")
    private String targetUri;

    @Size(max = 10, message = "{noMoreThan}")
    @Schema(description = "请求方法")
    private String requestMethod;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(description = "目标服务")
    private String targetServer;

    @Size(max = 10, message = "{noMoreThan}")
    @Schema(description = "响应code")
    private String code;

    @Schema(description = "响应时间")
    private Long time;

    @Size(max = 255, message = "{noMoreThan}")
    @Schema(description = "ip对应地址")
    private String location;


}