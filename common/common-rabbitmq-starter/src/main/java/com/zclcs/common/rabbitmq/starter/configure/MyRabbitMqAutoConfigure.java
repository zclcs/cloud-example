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
@ConditionalOnProperty(value = "my.lettuce.redis.enable", havingValue = "true", matchIfMissing = true)
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
     * canal 读取 binlog 更新缓存交换器
     */
    @Bean
    public DirectExchange dlxExChange() {
        return ExchangeBuilder.directExchange(RabbitConstant.DLX_EXCHANGE).build();
    }

    /**
     * 直接模式队列1 - 记录系统日志
     */
    @Bean
    public Queue directOneQueue() {
        return QueueBuilder.durable(RabbitConstant.QUEUE_SERVER_SYSTEM_LOG).build();
    }

    /**
     * canal 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_QUEUE).build();
    }

    /**
     * canal 读取 binlog 更新缓存交换器
     */
    @Bean
    public DirectExchange canalExChange() {
        return ExchangeBuilder.directExchange(RabbitConstant.CANAL_EXCHANGE).build();
    }

    /**
     * 直接模式绑定canal队列
     *
     * @param canalQueue    canal队列
     * @param canalExChange 直接模式交换器
     */
    @Bean
    public Binding canalExChangeBinding(Queue canalQueue, DirectExchange canalExChange) {
        return BindingBuilder.bind(canalQueue).to(canalExChange).with(RabbitConstant.CANAL_ROUTE_KEY);
    }

    /**
     * canal system 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalSystemQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_SYSTEM_QUEUE).deadLetterExchange(RabbitConstant.DLX_EXCHANGE).deadLetterRoutingKey(RabbitConstant.CANAL_SYSTEM_DLX_ROUTE_KEY).build();
    }

    /**
     * canal system 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalSystemDlxQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_SYSTEM_DLX_QUEUE).build();
    }

    /**
     * system 直接模式绑定canal队列
     *
     * @param canalSystemQueue canal队列
     * @param canalExChange    直接模式交换器
     */
    @Bean
    public Binding canalSystemExChangeBinding(Queue canalSystemQueue, DirectExchange canalExChange) {
        return BindingBuilder.bind(canalSystemQueue).to(canalExChange).with(RabbitConstant.CANAL_SYSTEM_ROUTE_KEY);
    }

    /**
     * system 直接模式绑定canal队列
     *
     * @param canalSystemDlxQueue canal队列
     * @param dlxExChange         直接模式交换器
     */
    @Bean
    public Binding canalSystemDlxExChangeBinding(Queue canalSystemDlxQueue, DirectExchange dlxExChange) {
        return BindingBuilder.bind(canalSystemDlxQueue).to(dlxExChange).with(RabbitConstant.CANAL_SYSTEM_DLX_ROUTE_KEY);
    }

    /**
     * canal dict 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalDictQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_DICT_QUEUE).deadLetterExchange(RabbitConstant.DLX_EXCHANGE).deadLetterRoutingKey(RabbitConstant.CANAL_DICT_DLX_ROUTE_KEY).build();
    }

    /**
     * canal dict 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalDictDlxQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_DICT_DLX_QUEUE).build();
    }

    /**
     * dict 直接模式绑定canal队列
     *
     * @param canalDictQueue canal队列
     * @param canalExChange  直接模式交换器
     */
    @Bean
    public Binding canalDictExChangeBinding(Queue canalDictQueue, DirectExchange canalExChange) {
        return BindingBuilder.bind(canalDictQueue).to(canalExChange).with(RabbitConstant.CANAL_DICT_ROUTE_KEY);
    }

    /**
     * dict 直接模式绑定canal队列
     *
     * @param canalDictDlxQueue canal队列
     * @param dlxExChange       直接模式交换器
     */
    @Bean
    public Binding canalDictDlxExChangeBinding(Queue canalDictDlxQueue, DirectExchange dlxExChange) {
        return BindingBuilder.bind(canalDictDlxQueue).to(dlxExChange).with(RabbitConstant.CANAL_DICT_DLX_ROUTE_KEY);
    }

    /**
     * canal generator 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalGeneratorQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_GENERATOR_QUEUE).deadLetterExchange(RabbitConstant.DLX_EXCHANGE).deadLetterRoutingKey(RabbitConstant.CANAL_GENERATOR_DLX_ROUTE_KEY).build();
    }

    /**
     * canal generator 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalGeneratorDlxQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_GENERATOR_DLX_QUEUE).build();
    }

    /**
     * generator 直接模式绑定canal队列
     *
     * @param canalGeneratorQueue canal队列
     * @param canalExChange       直接模式交换器
     */
    @Bean
    public Binding canalGeneratorExChangeBinding(Queue canalGeneratorQueue, DirectExchange canalExChange) {
        return BindingBuilder.bind(canalGeneratorQueue).to(canalExChange).with(RabbitConstant.CANAL_GENERATOR_ROUTE_KEY);
    }

    /**
     * generator 直接模式绑定canal队列
     *
     * @param canalGeneratorDlxQueue canal队列
     * @param dlxExChange            直接模式交换器
     */
    @Bean
    public Binding canalGeneratorDlxExChangeBinding(Queue canalGeneratorDlxQueue, DirectExchange dlxExChange) {
        return BindingBuilder.bind(canalGeneratorDlxQueue).to(dlxExChange).with(RabbitConstant.CANAL_GENERATOR_DLX_ROUTE_KEY);
    }

    /**
     * canal minio 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalMinioQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_MINIO_QUEUE).deadLetterExchange(RabbitConstant.DLX_EXCHANGE).deadLetterRoutingKey(RabbitConstant.CANAL_MINIO_DLX_ROUTE_KEY).build();
    }

    /**
     * canal minio 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalMinioDlxQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_MINIO_DLX_QUEUE).build();
    }

    /**
     * minio 直接模式绑定canal队列
     *
     * @param canalMinioQueue canal队列
     * @param canalExChange   直接模式交换器
     */
    @Bean
    public Binding canalMinioExChangeBinding(Queue canalMinioQueue, DirectExchange canalExChange) {
        return BindingBuilder.bind(canalMinioQueue).to(canalExChange).with(RabbitConstant.CANAL_MINIO_ROUTE_KEY);
    }

    /**
     * minio 直接模式绑定canal队列
     *
     * @param canalMinioDlxQueue canal队列
     * @param dlxExChange        直接模式交换器
     */
    @Bean
    public Binding canalMinioDlxExChangeBinding(Queue canalMinioDlxQueue, DirectExchange dlxExChange) {
        return BindingBuilder.bind(canalMinioDlxQueue).to(dlxExChange).with(RabbitConstant.CANAL_MINIO_DLX_ROUTE_KEY);
    }

    /**
     * canal test 读取 binlog 更新缓存队列
     */
    @Bean
    public Queue canalTestQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_TEST_QUEUE).deadLetterExchange(RabbitConstant.DLX_EXCHANGE).deadLetterRoutingKey(RabbitConstant.CANAL_TEST_DLX_ROUTE_KEY).build();
    }

    /**
     * canal test 死信队列
     */
    @Bean
    public Queue canalTestDlxQueue() {
        return QueueBuilder.durable(RabbitConstant.CANAL_TEST_DLX_QUEUE).build();
    }

    /**
     * test 直接模式绑定canal队列
     *
     * @param canalTestQueue canal队列
     * @param canalExChange  直接模式交换器
     */
    @Bean
    public Binding canalTestExChangeBinding(Queue canalTestQueue, DirectExchange canalExChange) {
        return BindingBuilder.bind(canalTestQueue).to(canalExChange).with(RabbitConstant.CANAL_TEST_ROUTE_KEY);
    }

    /**
     * test canal死信队列绑定
     *
     * @param canalTestDlxQueue canal队列
     * @param dlxExChange       直接模式交换器
     */
    @Bean
    public Binding canalTestDlxExChangeBinding(Queue canalTestDlxQueue, DirectExchange dlxExChange) {
        return BindingBuilder.bind(canalTestDlxQueue).to(dlxExChange).with(RabbitConstant.CANAL_TEST_DLX_ROUTE_KEY);
    }
}
