package com.zclcs.platform.system.biz.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zclcs.common.core.constant.RabbitConstant;
import com.zclcs.common.rabbitmq.starter.entity.MessageStruct;
import com.zclcs.platform.system.api.entity.ao.RateLimitLogAo;
import com.zclcs.platform.system.biz.service.RateLimitLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 系统限流日志 处理器
 * </p>
 *
 * @author zclcs
 */
@Slf4j
@Component
public class SystemRateLimitLogQueueHandler {

    private RateLimitLogService rateLimitLogService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setRateLimitLogService(RateLimitLogService rateLimitLogService) {
        this.rateLimitLogService = rateLimitLogService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.SYSTEM_RATE_LIMIT_LOG_QUEUE)
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //log.info("直接队列2，处理系统登录日志，手动ACK，接收消息：{}", messageStruct.getMessage());
            RateLimitLogAo logAo = objectMapper.readValue(messageStruct.getMessage(), RateLimitLogAo.class);
            rateLimitLogService.createRateLimitLog(logAo);
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                // 处理失败,直接丢弃这条日志
                channel.basicAck(deliveryTag, false);
            } catch (IOException e1) {
                log.error(e1.getMessage(), e1);
            }
        }
    }
}
