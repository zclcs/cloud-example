package com.zclcs.platform.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zclcs.platform.system.api.bean.ao.RouteLogAo;
import com.zclcs.platform.system.service.RouteLogService;
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
 * 系统网关转发日志 处理器
 * </p>
 *
 * @author zclcs
 */
@Slf4j
@Component
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

    @RabbitListener(queues = "${my.rabbit.mq.direct-queues.systemRouteLog.queue-name}", containerFactory = "batchRabbitListenerContainerFactory")
    public void saveSystemRouteLogQueueHandler(List<Message> messages, Channel channel) {
        List<RouteLogAo> batch = new ArrayList<>();
        for (Message message : messages) {
            final long deliveryTag = message.getMessageProperties().getDeliveryTag();
            String msg = new String(message.getBody());
            log.debug("处理系统网关转发日志，手动ACK，接收消息：{}", msg);
            try {
                RouteLogAo bean = objectMapper.readValue(msg, RouteLogAo.class);
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
//        try {
//            routeLogService.createRouteLogBatch(batch);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
    }
}
