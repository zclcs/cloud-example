package com.zclcs.common.rabbitmq.starter.configure;

import cn.hutool.core.util.StrUtil;
import com.zclcs.common.core.constant.CommonCore;
import com.zclcs.common.core.constant.RabbitMq;
import com.zclcs.common.core.constant.Strings;
import com.zclcs.common.core.enums.ExchangeType;
import com.zclcs.common.rabbitmq.starter.properties.MyRabbitMqProperties;
import com.zclcs.common.rabbitmq.starter.utils.RabbitKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * RabbitMQ配置，主要是配置队列，如果提前存在该队列，可以省略本配置类
 * </p>
 *
 * @author zclcs
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({MyRabbitMqProperties.class})
@ConditionalOnProperty(value = "my.rabbit.mq.enable", havingValue = "true", matchIfMissing = true)
public class MyRabbitMqAutoConfigure {

    private AmqpAdmin amqpAdmin;

    private MyRabbitMqProperties myRabbitMqProperties;

    public MyRabbitMqAutoConfigure(MyRabbitMqProperties myRabbitMqProperties, AmqpAdmin amqpAdmin) {
        this.myRabbitMqProperties = myRabbitMqProperties;
        this.amqpAdmin = amqpAdmin;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnsCallback((returned) ->
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage()));
        return rabbitTemplate;
    }

    @Bean
    public void initMqSetting() {
        Map<String, MyRabbitMqProperties.DirectQueue> directQueues = Optional.ofNullable(myRabbitMqProperties.getDirectQueues()).orElse(new HashMap<>());
        for (MyRabbitMqProperties.DirectQueue directQueue : directQueues.values()) {
            String queueName = directQueue.getQueueName();
            String exchangeName = directQueue.getExchangeName();
            String routeKey = directQueue.getRouteKey();
            String dlxQueueName = directQueue.getDlxQueueName();
            String dlxExchangeName = directQueue.getDlxExchangeName();
            String dlxRouteKey = directQueue.getDlxRouteKey();
            if (directQueue.getNameBaseOnQueueName()) {
                exchangeName = RabbitKeyUtil.getExchangeName(queueName, ExchangeType.DIRECT);
                routeKey = RabbitKeyUtil.getRouteKey(queueName);
                dlxExchangeName = RabbitKeyUtil.getDlxExchangeName(queueName, ExchangeType.DIRECT);
                dlxRouteKey = RabbitKeyUtil.getDlxRouteKey(queueName);
            }
            definition(queueName, exchangeName, routeKey, dlxQueueName, dlxExchangeName, dlxRouteKey, directQueue.getInitDlx());
        }

        Map<String, MyRabbitMqProperties.FanoutQueue> fanoutQueues = Optional.ofNullable(myRabbitMqProperties.getFanoutQueues()).orElse(new HashMap<>());
        for (MyRabbitMqProperties.FanoutQueue fanoutQueue : fanoutQueues.values()) {
            Queue queue = QueueBuilder.durable(fanoutQueue.getQueueName()).build();
            Exchange exchange = ExchangeBuilder.fanoutExchange(fanoutQueue.getExchangeName()).build();
            Binding binding = BindingBuilder.bind(queue).to(exchange).with("").noargs();
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(binding);
        }

        Map<String, MyRabbitMqProperties.TopicQueue> topicQueues = Optional.ofNullable(myRabbitMqProperties.getTopicQueues()).orElse(new HashMap<>());
        for (MyRabbitMqProperties.TopicQueue topicQueue : topicQueues.values()) {
            String queueName = topicQueue.getQueueName();
            String exchangeName = topicQueue.getExchangeName();
            String routeKey = topicQueue.getRouteKey();
            String dlxQueueName = topicQueue.getDlxQueueName();
            String dlxExchangeName = topicQueue.getDlxExchangeName();
            String dlxRouteKey = topicQueue.getDlxRouteKey();
            if (topicQueue.getNameBaseOnQueueName()) {
                exchangeName = RabbitKeyUtil.getExchangeName(queueName, ExchangeType.TOPIC);
                routeKey = RabbitKeyUtil.getRouteKey(queueName);
                dlxExchangeName = RabbitKeyUtil.getDlxExchangeName(queueName, ExchangeType.TOPIC);
                dlxRouteKey = RabbitKeyUtil.getDlxRouteKey(queueName);
            }
            definition(queueName, exchangeName, routeKey, dlxQueueName, dlxExchangeName, dlxRouteKey, topicQueue.getInitDlx());
        }

        Map<String, MyRabbitMqProperties.DelayedQueue> delayedQueues = Optional.ofNullable(myRabbitMqProperties.getDelayedQueues()).orElse(new HashMap<>());
        for (MyRabbitMqProperties.DelayedQueue delayedQueue : delayedQueues.values()) {
            String queueName = delayedQueue.getQueueName();
            String exchangeName = delayedQueue.getExchangeName();
            String routeKey = delayedQueue.getRouteKey();
            if (delayedQueue.getNameBaseOnQueueName()) {
                exchangeName = RabbitKeyUtil.getExchangeName(queueName, ExchangeType.DELAYED);
                routeKey = RabbitKeyUtil.getRouteKey(queueName);
            }
            Queue queue = QueueBuilder.durable(delayedQueue.getQueueName()).build();
            CustomExchange exchange = new CustomExchange(exchangeName, "x-delayed-message", true, false, delayedArgs());
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(routeKey).noargs();
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(binding);
        }
    }

    private void definition(String queueName, String exchangeName, String routeKey, String dlxQueueName, String dlxExchangeName, String dlxRouteKey, Boolean initDlx) {
        Queue queue = QueueBuilder.durable(queueName).build();
        Exchange exchange = ExchangeBuilder.directExchange(exchangeName).build();
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareExchange(exchange);
        BindingBuilder.GenericArgumentsConfigurer with = BindingBuilder.bind(queue).to(exchange).with(routeKey);
        Binding normal;
        if (initDlx) {
            Queue dlxQueue = QueueBuilder.durable(dlxQueueName).build();
            Exchange dlxExchange = ExchangeBuilder.directExchange(dlxExchangeName).build();
            Binding dlx = BindingBuilder.bind(dlxQueue).to(dlxExchange).with(dlxRouteKey).noargs();
            amqpAdmin.declareQueue(dlxQueue);
            amqpAdmin.declareExchange(dlxExchange);
            amqpAdmin.declareBinding(dlx);
            normal = with.and(dlxArgs(dlxExchangeName, dlxRouteKey));
        } else {
            normal = with.noargs();
        }
        amqpAdmin.declareBinding(normal);
    }

    /**
     * 死信队列设置
     *
     * @param dlxExchangeName 死信队列交换机
     * @param dlxRoutingKey 死信队列路由key
     * @return 队列设置
     */
    private Map<String, Object> dlxArgs(String dlxExchangeName, String dlxRoutingKey) {
        Map<String, Object> map = new HashMap<>();
        // 绑定该队列到死信交换机
        map.put("x-dead-letter-exchange", dlxExchangeName);
        // 绑定该队列到死信路由key
        map.put("x-dead-letter-routing-key", dlxRoutingKey);
        // 队列设置消息过期时间秒
        map.put("x-message-ttl", 7 * 24 * 60 * 60);
        return map;
    }

    /**
     * 延迟队列设置
     *
     * @return 队列设置
     */
    private Map<String, Object> delayedArgs() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-delayed-type", "direct");
        return map;
    }

}
