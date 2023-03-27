package com.zclcs.common.rabbitmq.starter.configure;

import com.zclcs.common.core.constant.RabbitConstant;
import com.zclcs.common.rabbitmq.starter.properties.MyRabbitMqProperties;
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

    private MyRabbitMqProperties myRabbitMqProperties;

    public MyRabbitMqAutoConfigure(MyRabbitMqProperties myRabbitMqProperties) {
        this.myRabbitMqProperties = myRabbitMqProperties;
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

    /**
     * dlx 死信交换器
     */
    @Bean
    public DirectExchange dlxExChange() {
        return ExchangeBuilder.directExchange(RabbitConstant.DLX_EXCHANGE).build();
    }

    /**
     * 直接队列交换器
     */
    @Bean
    public DirectExchange directExChange() {
        return ExchangeBuilder.directExchange(RabbitConstant.DIRECT_EXCHANGE).build();
    }

    /**
     * 记录系统日志
     */
    @Bean
    public Queue systemLogQueue() {
        return QueueBuilder.durable(RabbitConstant.SYSTEM_LOG_QUEUE).build();
    }

    /**
     * 记录系统登录日志
     */
    @Bean
    public Queue systemLoginLogQueue() {
        return QueueBuilder.durable(RabbitConstant.SYSTEM_LOGIN_LOG_QUEUE).build();
    }

    /**
     * 记录系统网关转发日志
     */
    @Bean
    public Queue systemRouteLogQueue() {
        return QueueBuilder.durable(RabbitConstant.SYSTEM_ROUTE_LOG_QUEUE).build();
    }

    /**
     * 记录系统黑名单日志
     */
    @Bean
    public Queue systemBlockLogQueue() {
        return QueueBuilder.durable(RabbitConstant.SYSTEM_BLOCK_LOG_QUEUE).build();
    }

    /**
     * 记录系统限流日志
     */
    @Bean
    public Queue systemRateLimitLogQueue() {
        return QueueBuilder.durable(RabbitConstant.SYSTEM_RATE_LIMIT_LOG_QUEUE).build();
    }

    /**
     * 记录系统字典刷新队列
     */
    @Bean
    public Queue systemDictRefreshQueue() {
        return QueueBuilder.durable(RabbitConstant.SYSTEM_DICT_REFRESH_QUEUE).build();
    }

    @Bean
    public Binding systemLogQueueBinding() {
        return BindingBuilder.bind(systemLogQueue()).to(directExChange()).with(RabbitConstant.SYSTEM_LOG_ROUTE_KEY);
    }

    @Bean
    public Binding systemLoginLogBinding() {
        return BindingBuilder.bind(systemLoginLogQueue()).to(directExChange()).with(RabbitConstant.SYSTEM_LOGIN_LOG_ROUTE_KEY);
    }

    @Bean
    public Binding systemRouteLogQueueBinding() {
        return BindingBuilder.bind(systemRouteLogQueue()).to(directExChange()).with(RabbitConstant.SYSTEM_ROUTE_LOG_ROUTE_KEY);
    }

    @Bean
    public Binding systemBlockLogBinding() {
        return BindingBuilder.bind(systemBlockLogQueue()).to(directExChange()).with(RabbitConstant.SYSTEM_BLOCK_LOG_LOG_ROUTE_KEY);
    }

    @Bean
    public Binding systemRateLimitLogBinding() {
        return BindingBuilder.bind(systemRateLimitLogQueue()).to(directExChange()).with(RabbitConstant.SYSTEM_RATE_LIMIT_LOG_ROUTE_KEY);
    }

    @Bean
    public Binding systemDictRefreshBinding() {
        return BindingBuilder.bind(systemDictRefreshQueue()).to(directExChange()).with(RabbitConstant.SYSTEM_DICT_REFRESH_ROUTING_KEY);
    }
}
