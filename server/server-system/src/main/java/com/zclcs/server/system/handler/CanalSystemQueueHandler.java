package com.zclcs.server.system.handler;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.zclcs.common.core.constant.RabbitConstant;
import com.zclcs.common.core.entity.CanalBinLogInfo;
import com.zclcs.common.core.properties.GlobalProperties;
import com.zclcs.common.core.service.HandleCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * canal 动态topic 更新缓存队列
 * </p>
 *
 * @author zclcs
 */
@Slf4j
@Component
public class CanalSystemQueueHandler {

    private GlobalProperties globalProperties;
    private HandleCacheService handleServerSystemCacheService;

    @Autowired
    public void setGlobalProperties(GlobalProperties globalProperties) {
        this.globalProperties = globalProperties;
    }

    @Autowired
    @Qualifier(value = "handleServerSystemCacheService")
    public void setHandleServerSystemCacheService(HandleCacheService handleServerSystemCacheService) {
        this.handleServerSystemCacheService = handleServerSystemCacheService;
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = RabbitConstant.CANAL_SYSTEM_QUEUE, durable = "true", arguments = {
                            @Argument(name = RabbitConstant.DLX_EXCHANGE_PRE, value = RabbitConstant.DLX_EXCHANGE),
                            @Argument(name = RabbitConstant.DLX_ROUTE_KEY_PRE, value = RabbitConstant.CANAL_SYSTEM_DLX_ROUTE_KEY)
                    }),
                    exchange = @Exchange(value = RabbitConstant.CANAL_EXCHANGE),
                    key = RabbitConstant.CANAL_SYSTEM_ROUTE_KEY
            )
    })
    public void directHandlerManualAck(String messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            CanalBinLogInfo canalBinLogInfo = JSONUtil.toBean(messageStruct, CanalBinLogInfo.class);
            log.info("canal system 读取 binlog，手动ACK，接收消息：{}", JSONUtil.toJsonStr(canalBinLogInfo));
            String database = canalBinLogInfo.getDatabase();
            if (database.equals(globalProperties.getDatabase().getServerSystem())) {
                handleServerSystemCacheService.handleCache(canalBinLogInfo, deliveryTag, channel);
            } else {
                // 库名不对 ack掉
                channel.basicAck(deliveryTag, false);
            }
        } catch (Exception e) {
            try {
                // 处理失败,进入死信队列
                channel.basicReject(deliveryTag, false);
            } catch (IOException e1) {
                log.error(e1.getMessage(), e1);
            }
            log.error(e.getMessage(), e);
        }
    }

}
