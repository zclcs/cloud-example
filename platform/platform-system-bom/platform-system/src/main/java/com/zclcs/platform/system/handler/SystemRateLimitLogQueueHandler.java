package com.zclcs.platform.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zclcs.platform.system.api.bean.ao.RateLimitLogAo;
import com.zclcs.platform.system.service.RateLimitLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @RabbitListener(queues = "${my.rabbit.mq.direct-queues.systemRateLimitLog.queue-name}", containerFactory = "batchRabbitListenerContainerFactory")
    public void saveSystemRateLimitLogQueueHandler(List<Message> messages, Channel channel) {
        List<RateLimitLogAo> batch = new ArrayList<>();
        for (Message message : messages) {
            final long deliveryTag = message.getMessageProperties().getDeliveryTag();
            String msg = new String(message.getBody());
            log.debug("处理系统限流日志，手动ACK，接收消息：{}", msg);
            try {
                RateLimitLogAo bean = objectMapper.readValue(msg, RateLimitLogAo.class);
                batch.add(bean);
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
        try {
            rateLimitLogService.createRateLimitLogBatch(batch);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
