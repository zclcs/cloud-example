package com.zclcs.server.generator.handler;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.zclcs.common.core.constant.RabbitConstant;
import com.zclcs.common.core.entity.CanalBinLogInfo;
import com.zclcs.common.core.properties.GlobalProperties;
import com.zclcs.common.core.service.HandleCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 直接队列1 处理器
 * </p>
 *
 * @author zclcs
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CanalGeneratorQueueHandler {

    private GlobalProperties globalProperties;
    private HandleCacheService handleServerGeneratorCacheService;

    @Autowired
    public void setGlobalProperties(GlobalProperties globalProperties) {
        this.globalProperties = globalProperties;
    }

    @Autowired
    @Qualifier(value = "handleServerGeneratorCacheService")
    public void setHandleServerGeneratorCacheService(HandleCacheService handleServerGeneratorCacheService) {
        this.handleServerGeneratorCacheService = handleServerGeneratorCacheService;
    }

    @RabbitListener(queues = RabbitConstant.CANAL_GENERATOR_QUEUE)
    public void cacheHandlerManualAck(String messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            CanalBinLogInfo canalBinLogInfo = JSONUtil.toBean(messageStruct, CanalBinLogInfo.class);
            log.info("canal generator 读取 binlog，手动ACK，接收消息：{}", JSONUtil.toJsonStr(canalBinLogInfo));
            String database = canalBinLogInfo.getDatabase();
            if (database.equals(globalProperties.getDatabase().getServerGenerator())) {
                handleServerGeneratorCacheService.handleCache(canalBinLogInfo, deliveryTag, channel);
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

    @RabbitListener(queues = RabbitConstant.CANAL_GENERATOR_DLX_QUEUE)
    public void dlxHandler(String messageStruct, Message message, Channel channel) {
        // 正常队列没处理完成，便会进入该队列，在处理一次，报错要通知开发或者运维，处理完报错后，重新推送消息
        // 可能的报错：数据库挂了，或者别的问题
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        CanalBinLogInfo canalBinLogInfo = JSONUtil.toBean(messageStruct, CanalBinLogInfo.class);
        log.info("canal generator 死信队列 接收消息：{}", JSONUtil.toJsonStr(canalBinLogInfo));
        String database = canalBinLogInfo.getDatabase();
        if (database.equals(globalProperties.getDatabase().getServerSystem())) {
            try {
                handleServerGeneratorCacheService.handleCache(canalBinLogInfo, deliveryTag, channel);
            } catch (Exception e) {
                log.error("canal generator 死信队列 接收消息：{}, 出现异常 {}", messageStruct, e.getMessage(), e);
            }
        }
    }

}
