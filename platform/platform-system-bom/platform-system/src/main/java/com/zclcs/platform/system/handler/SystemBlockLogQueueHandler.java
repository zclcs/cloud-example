package com.zclcs.platform.system.handler;

import com.rabbitmq.client.Channel;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.platform.system.api.bean.ao.BlockLogAo;
import com.zclcs.platform.system.service.BlockLogService;
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
 * 系统黑名单日志 处理器
 * </p>
 *
 * @author zclcs
 */
@Slf4j
@Component
public class SystemBlockLogQueueHandler {

    private BlockLogService blockLogService;

    @Autowired
    public void setBlockLogService(BlockLogService blockLogService) {
        this.blockLogService = blockLogService;
    }

    @RabbitListener(queues = "${my.rabbit.mq.direct-queues.systemBlockLog.queue-name}", containerFactory = "batchRabbitListenerContainerFactory")
    public void saveSystemBlockLogQueueHandler(List<Message> messages, Channel channel) {
        List<BlockLogAo> batch = new ArrayList<>();
        for (Message message : messages) {
            final long deliveryTag = message.getMessageProperties().getDeliveryTag();
            String msg = new String(message.getBody());
            log.debug("处理系统黑名单日志，手动ACK，接收消息：{}", msg);
            try {
                BlockLogAo bean = JsonUtil.readValue(msg, BlockLogAo.class);
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
            blockLogService.createBlockLogBatch(batch);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
