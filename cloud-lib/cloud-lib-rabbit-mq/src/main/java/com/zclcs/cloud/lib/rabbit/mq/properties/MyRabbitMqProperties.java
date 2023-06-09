package com.zclcs.cloud.lib.rabbit.mq.properties;

import com.zclcs.cloud.lib.core.enums.ExchangeType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

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

    /**
     * 直通队列
     * {@link ExchangeType}
     */
    private Map<String, DirectQueue> directQueues;

    /**
     * 扇形队列
     * {@link ExchangeType}
     */
    private Map<String, FanoutQueue> fanoutQueues;

    /**
     * 主题队列
     * {@link ExchangeType}
     */
    private Map<String, TopicQueue> topicQueues;

    /**
     * 延迟队列
     * {@link ExchangeType}
     */
    private Map<String, DelayedQueue> delayedQueues;

    @Data
    public static class DirectQueue {

        /**
         * 创建交换机、路由key的名称是否根据队列名称来
         */
        private Boolean nameBaseOnQueueName;

        /**
         * 直通队列名称
         */
        private String queueName;

        /**
         * 直通队列交换机名称
         */
        private String exchangeName;

        /**
         * 直通队列路由key名称
         */
        private String routeKey;

        /**
         * 是否创建死信队列
         */
        private Boolean initDlx = false;

        /**
         * 死信队列名称
         */
        private String dlxQueueName;

        /**
         * 死信队列交换机名称
         */
        private String dlxExchangeName;

        /**
         * 死信队列路由key名称
         */
        private String dlxRouteKey;

        /**
         * 消息过期时间(毫秒)
         */
        private Integer ttl = 0;
    }

    @Data
    public static class FanoutQueue {

        /**
         * 扇形队列交换机名称
         */
        private String exchangeName;

        /**
         * 扇形队列名称
         */
        private String queueName;
    }

    @Data
    public static class TopicQueue {

        /**
         * 创建交换机、路由key的名称是否根据队列名称来
         */
        private Boolean nameBaseOnQueueName;

        /**
         * 主题队列名称
         */
        private String queueName;

        /**
         * 主题队列交换机名称
         */
        private String exchangeName;

        /**
         * 主题队列路由key名称
         */
        private String routeKey;

        /**
         * 是否创建死信队列
         */
        private Boolean initDlx = false;

        /**
         * 死信队列交换机名称
         */
        private String dlxExchangeName;

        /**
         * 死信队列名称
         */
        private String dlxQueueName;

        /**
         * 死信队列路由key名称
         */
        private String dlxRouteKey;

        /**
         * 消息过期时间(毫秒)
         */
        private Integer ttl = 0;
    }

    @Data
    public static class DelayedQueue {

        /**
         * 创建交换机、路由key的名称是否根据队列名称来
         */
        private Boolean nameBaseOnQueueName;

        /**
         * 延迟队列交换机名称
         */
        private String exchangeName;

        /**
         * 延迟队列名称
         */
        private String queueName;

        /**
         * 延迟队列路由key名称
         */
        private String routeKey;
    }

}
