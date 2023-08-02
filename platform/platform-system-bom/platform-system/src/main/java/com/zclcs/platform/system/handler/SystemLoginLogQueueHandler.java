package com.zclcs.platform.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.platform.system.api.bean.ao.LoginLogAo;
import com.zclcs.platform.system.service.LoginLogService;
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
 * 系统登录日志 处理器
 * </p>
 *
 * @author zclcs
 */
@Slf4j
@Component
@RabbitListener(queues = "${my.rabbit.mq.direct-queues.systemLoginLog.queue-name}", concurrency = "5")
public class SystemLoginLogQueueHandler {

    private LoginLogService loginLogService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setLoginLogService(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Async(CommonCore.ASYNC_POOL)
    @RabbitHandler
    public void directHandlerManualAck(String messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.debug("处理系统登录日志，手动ACK，接收消息：{}", messageStruct);
            LoginLogAo loginLogAo = objectMapper.readValue(messageStruct, LoginLogAo.class);
            loginLogService.createLoginLog(loginLogAo);
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
