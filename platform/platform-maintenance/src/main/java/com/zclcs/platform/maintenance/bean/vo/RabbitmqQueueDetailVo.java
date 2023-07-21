package com.zclcs.platform.maintenance.bean.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * NacosTokenVo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "RabbitmqQueueDetailVo", description = "RabbitmqQueueDetailVo")
public class RabbitmqQueueDetailVo {

    @Schema(description = "交换机")
    private String source;

    @Schema(description = "命名空间")
    private String vhost;

    @Schema(description = "绑定的队列名称")
    private String destination;

    @Schema(description = "绑定的队列类型")
    @JsonAlias("destination_type")
    private String destinationType;

    @Schema(description = "路由key")
    @JsonAlias("routing_key")
    private String routingKey;

    @Schema(description = "额外参数")
    private Map<String, Object> arguments;

    @Schema(description = "路由key 需要url解码")
    @JsonAlias("properties_key")
    private String propertiesKey;
}
