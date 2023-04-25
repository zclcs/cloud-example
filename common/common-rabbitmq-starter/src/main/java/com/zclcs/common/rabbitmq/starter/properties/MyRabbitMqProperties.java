package com.zclcs.common.rabbitmq.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zclcs
 */
@Data
@ConfigurationProperties(prefix = "my.rabbit.mq")
public class MyRabbitMqProperties {

    /**
     * 是否开启
     */
    private Boolean enable = true;

    private String dlxExchange;

    private String directExchange;

    private String systemLogQueue;

    private String systemLogQueueBinding;

    private String systemLoginLogQueue;

    private String systemLoginLogBinding;

    private String systemRouteLogQueue;

    private String systemRouteLogQueueBinding;

    private String systemBlockLogQueue;

    private String systemBlockLogBinding;

    private String systemRateLimitLogQueue;

    private String systemRateLimitLogBinding;
}
