package com.zclcs.platform.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.rabbit.mq.entity.MessageStruct;
import com.zclcs.platform.system.api.entity.ao.RouteLogAo;
import com.zclcs.platform.system.service.RouteLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 系统网关转发日志 处理器
 * </p>
 *
 * @author zclcs
 */
@Slf4j
@Component
@RabbitListener(queues = "${my.rabbit.mq.direct-queues.systemRouteLog.queue-name}", concurrency = "5")
public class SystemRouteLogQueueHandler {

    private RouteLogService routeLogService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setRouteLogService(RouteLogService routeLogService) {
        this.routeLogService = routeLogService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Async(CommonCore.ASYNC_POOL)
    @RabbitHandler
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.debug("处理系统网关转发日志，手动ACK，接收消息：{}", messageStruct.getMessage());
            RouteLogAo routeLogAo = objectMapper.readValue(messageStruct.getMessage(), RouteLogAo.class);
            routeLogService.createRouteLog(routeLogAo);
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
