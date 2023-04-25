package com.zclcs.common.rabbitmq.starter.configure;

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
    public DirectExchange dlxExchange() {
        return ExchangeBuilder.directExchange(myRabbitMqProperties.getDlxExchange()).build();
    }

    /**
     * 直接队列交换器
     */
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(myRabbitMqProperties.getDirectExchange()).build();
    }

    /**
     * 记录系统日志
     */
    @Bean
    public Queue systemLogQueue() {
        return QueueBuilder.durable(myRabbitMqProperties.getSystemLogQueue()).build();
    }

    @Bean
    public Binding systemLogQueueBinding() {
        return BindingBuilder.bind(systemLogQueue()).to(directExchange()).with(myRabbitMqProperties.getSystemLogQueueBinding());
    }

    /**
     * 记录系统登录日志
     */
    @Bean
    public Queue systemLoginLogQueue() {
        return QueueBuilder.durable(myRabbitMqProperties.getSystemLoginLogQueue()).build();
    }

    @Bean
    public Binding systemLoginLogBinding() {
        return BindingBuilder.bind(systemLoginLogQueue()).to(directExchange()).with(myRabbitMqProperties.getSystemLoginLogBinding());
    }

    /**
     * 记录系统网关转发日志
     */
    @Bean
    public Queue systemRouteLogQueue() {
        return QueueBuilder.durable(myRabbitMqProperties.getSystemRouteLogQueue()).build();
    }

    @Bean
    public Binding systemRouteLogQueueBinding() {
        return BindingBuilder.bind(systemRouteLogQueue()).to(directExchange()).with(myRabbitMqProperties.getSystemRouteLogQueueBinding());
    }

    /**
     * 记录系统黑名单日志
     */
    @Bean
    public Queue systemBlockLogQueue() {
        return QueueBuilder.durable(myRabbitMqProperties.getSystemBlockLogQueue()).build();
    }

    @Bean
    public Binding systemBlockLogBinding() {
        return BindingBuilder.bind(systemBlockLogQueue()).to(directExchange()).with(myRabbitMqProperties.getSystemBlockLogBinding());
    }

    /**
     * 记录系统限流日志
     */
    @Bean
    public Queue systemRateLimitLogQueue() {
        return QueueBuilder.durable(myRabbitMqProperties.getSystemRateLimitLogQueue()).build();
    }

    @Bean
    public Binding systemRateLimitLogBinding() {
        return BindingBuilder.bind(systemRateLimitLogQueue()).to(directExchange()).with(myRabbitMqProperties.getSystemRateLimitLogBinding());
    }
}
