package com.zclcs.platform.maintenance.bean.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * RabbitmqExchangeDetailVo
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
public class RabbitmqExchangeDetailVo {

    /**
     * 交换机
     */
    private String source;

    /**
     * 命名空间
     */
    private String vhost;

    /**
     * 绑定的队列名称
     */
    private String destination;

    /**
     * 绑定的队列类型
     */
    @JsonAlias("destination_type")
    private String destinationType;

    /**
     * 路由key
     */
    @JsonAlias("routing_key")
    private String routingKey;

    /**
     * 额外参数
     */
    private Map<String, Object> arguments;

    /**
     * 路由key 需要url解码
     */
    @JsonAlias("properties_key")
    private String propertiesKey;
}
