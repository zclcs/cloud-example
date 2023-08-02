package com.zclcs.cloud.lib.rabbit.mq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.rabbit.mq.properties.MyRabbitMqProperties;
import com.zclcs.cloud.lib.rabbit.mq.utils.RabbitKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> void convertAndSend(MyRabbitMqProperties.DirectQueue directQueue, T message) {
        rabbitTemplate.convertAndSend(RabbitKeyUtil.getDirectExchangeName(directQueue),
                RabbitKeyUtil.getDirectRouteKey(directQueue), objectMapper.writeValueAsString(message));
    }

    @SneakyThrows
    public <T> void convertAndSend(MyRabbitMqProperties.FanoutQueue fanoutQueue, T message) {
        rabbitTemplate.convertAndSend(fanoutQueue.getExchangeName(), objectMapper.writeValueAsString(message));
    }

    @SneakyThrows
    public <T> void convertAndSend(MyRabbitMqProperties.TopicQueue topicQueue, T message) {
        rabbitTemplate.convertAndSend(RabbitKeyUtil.getTopicExchangeName(topicQueue),
                RabbitKeyUtil.getTopicRouteKey(topicQueue), objectMapper.writeValueAsString(message));
    }

    /**
     * 发送延迟消息
     *
     * @param delayTime 延迟时间 不能超过 int 最大值 2的31次方-1 大概42天
     */
    @SneakyThrows
    public <T> void convertAndSend(MyRabbitMqProperties.DelayedQueue delayedQueue, T message, int delayTime) {
        rabbitTemplate.convertAndSend(RabbitKeyUtil.getDelayedExchangeName(delayedQueue),
                RabbitKeyUtil.getDelayedRouteKey(delayedQueue), objectMapper.writeValueAsString(message),
                messageSetting -> {
                    //设置消息持久化
                    messageSetting.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    //设置延时时间
                    messageSetting.getMessageProperties().setHeader("x-delay", delayTime);
                    return messageSetting;
                });
    }

}
