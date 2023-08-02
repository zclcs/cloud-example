package com.zclcs.cloud.lib.rabbit.mq.configure;

import cn.hutool.core.collection.CollectionUtil;
import com.zclcs.cloud.lib.core.enums.ExchangeType;
import com.zclcs.cloud.lib.rabbit.mq.properties.MyRabbitMqProperties;
import com.zclcs.cloud.lib.rabbit.mq.utils.RabbitKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
//        factory.setConsumerBatchEnabled(true);
//        factory.setBatchSize(20);
        return factory;
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                log.debug("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnsCallback((returned) ->
                log.error("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage()));
        return rabbitTemplate;
    }

    @Bean
    public void initMqSetting() {
        Map<String, MyRabbitMqProperties.DirectQueue> directQueues = Optional.ofNullable(myRabbitMqProperties.getDirectQueues()).orElse(new HashMap<>());
        for (MyRabbitMqProperties.DirectQueue directQueue : directQueues.values()) {
            String queueName = directQueue.getQueueName();
            String exchangeName = RabbitKeyUtil.getDirectExchangeName(directQueue);
            String routeKey = RabbitKeyUtil.getDirectRouteKey(directQueue);
            String dlxQueueName = directQueue.getDlxQueueName();
            String dlxExchangeName = RabbitKeyUtil.getDirectDlxExchangeName(directQueue);
            String dlxRouteKey = RabbitKeyUtil.getDirectDlxRouteKey(directQueue);
            definition(ExchangeType.DIRECT, queueName, exchangeName, routeKey, dlxQueueName, dlxExchangeName, dlxRouteKey, directQueue.getInitDlx(), directQueue.getTtl(), null);
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
            String exchangeName = RabbitKeyUtil.getTopicExchangeName(topicQueue);
            String routeKey = RabbitKeyUtil.getTopicRouteKey(topicQueue);
            String dlxQueueName = topicQueue.getDlxQueueName();
            String dlxExchangeName = RabbitKeyUtil.getTopicDlxExchangeName(topicQueue);
            String dlxRouteKey = RabbitKeyUtil.getTopicDlxRouteKey(topicQueue);
            definition(ExchangeType.TOPIC, queueName, exchangeName, routeKey, dlxQueueName, dlxExchangeName, dlxRouteKey, topicQueue.getInitDlx(), topicQueue.getTtl(), topicQueue.getDistribution());
        }

        Map<String, MyRabbitMqProperties.DelayedQueue> delayedQueues = Optional.ofNullable(myRabbitMqProperties.getDelayedQueues()).orElse(new HashMap<>());
        for (MyRabbitMqProperties.DelayedQueue delayedQueue : delayedQueues.values()) {
            String queueName = delayedQueue.getQueueName();
            String exchangeName = RabbitKeyUtil.getDelayedExchangeName(delayedQueue);
            String routeKey = RabbitKeyUtil.getDelayedRouteKey(delayedQueue);
            Queue queue = QueueBuilder.durable(queueName).build();
            CustomExchange exchange = new CustomExchange(exchangeName, "x-delayed-message", true, false, delayedArgs());
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(routeKey).noargs();
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(binding);
        }
    }

    private void definition(ExchangeType exchangeType, String queueName, String exchangeName, String routeKey, String dlxQueueName, String dlxExchangeName, String dlxRouteKey, Boolean initDlx, Integer ttl, Map<String, MyRabbitMqProperties.TopicQueue> distributionTopicQueues) {
        QueueBuilder durable = QueueBuilder.durable(queueName);
        if (ttl != 0) {
            durable.ttl(ttl);
        }
        if (initDlx) {
            Queue dlxQueue = QueueBuilder.durable(dlxQueueName).build();
            Exchange dlxExchange = ExchangeBuilder.directExchange(dlxExchangeName).build();
            Binding dlx = BindingBuilder.bind(dlxQueue).to(dlxExchange).with(dlxRouteKey).noargs();
            amqpAdmin.declareQueue(dlxQueue);
            amqpAdmin.declareExchange(dlxExchange);
            amqpAdmin.declareBinding(dlx);
            durable.deadLetterExchange(dlxExchangeName);
            durable.deadLetterRoutingKey(dlxRouteKey);
        }
        Exchange exchange;
        if (exchangeType.getSubPrefix().equals(ExchangeType.DIRECT.getSubPrefix())) {
            exchange = ExchangeBuilder.directExchange(exchangeName).build();
        } else {
            exchange = ExchangeBuilder.topicExchange(exchangeName).build();
        }
        amqpAdmin.declareExchange(exchange);
        Queue queue = durable.build();
        amqpAdmin.declareQueue(queue);
        Binding normal = BindingBuilder.bind(queue).to(exchange).with(routeKey).noargs();
        amqpAdmin.declareBinding(normal);
        if (exchangeType.getSubPrefix().equals(ExchangeType.TOPIC.getSubPrefix()) && CollectionUtil.isNotEmpty(distributionTopicQueues)) {
            for (MyRabbitMqProperties.TopicQueue distributionTopic : distributionTopicQueues.values()) {
                String distributionQueueName = distributionTopic.getQueueName();
                String distributionDlxQueueName = distributionTopic.getDlxQueueName();
                String distributionDlxExchangeName = RabbitKeyUtil.getTopicDlxExchangeName(distributionTopic);
                String distributionDlxRouteKey = RabbitKeyUtil.getTopicDlxRouteKey(distributionTopic);
                definition(ExchangeType.TOPIC, distributionQueueName, exchangeName, routeKey, distributionDlxQueueName, distributionDlxExchangeName, distributionDlxRouteKey, distributionTopic.getInitDlx(), distributionTopic.getTtl(), distributionTopic.getDistribution());
            }
        }
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
