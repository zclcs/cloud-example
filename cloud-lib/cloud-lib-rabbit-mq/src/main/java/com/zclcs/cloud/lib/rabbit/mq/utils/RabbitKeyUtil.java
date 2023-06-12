package com.zclcs.cloud.lib.rabbit.mq.utils;

import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.RabbitMq;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.enums.ExchangeType;
import com.zclcs.cloud.lib.rabbit.mq.properties.MyRabbitMqProperties;
import lombok.experimental.UtilityClass;

/**
 * @author zclcs
 */
@UtilityClass
public class RabbitKeyUtil {

    public String getDirectExchangeName(MyRabbitMqProperties.DirectQueue directQueue) {
        if (directQueue.getNameBaseOnQueueName()) {
            return getExchangeName(directQueue.getQueueName(), ExchangeType.DIRECT);
        } else {
            return directQueue.getExchangeName();
        }
    }

    public String getDirectRouteKey(MyRabbitMqProperties.DirectQueue directQueue) {
        if (directQueue.getNameBaseOnQueueName()) {
            return getRouteKey(directQueue.getQueueName());
        } else {
            return directQueue.getRouteKey();
        }
    }

    public String getDirectDlxExchangeName(MyRabbitMqProperties.DirectQueue directQueue) {
        if (directQueue.getNameBaseOnQueueName()) {
            return getDlxExchangeName(directQueue.getQueueName(), ExchangeType.DIRECT);
        } else {
            return directQueue.getDlxExchangeName();
        }
    }

    public String getDirectDlxRouteKey(MyRabbitMqProperties.DirectQueue directQueue) {
        if (directQueue.getNameBaseOnQueueName()) {
            return getDlxRouteKey(directQueue.getQueueName());
        } else {
            return directQueue.getDlxRouteKey();
        }
    }

    public String getTopicExchangeName(MyRabbitMqProperties.TopicQueue topicQueue) {
        if (topicQueue.getNameBaseOnQueueName()) {
            return getExchangeName(topicQueue.getQueueName(), ExchangeType.TOPIC);
        } else {
            return topicQueue.getExchangeName();
        }
    }

    public String getTopicRouteKey(MyRabbitMqProperties.TopicQueue topicQueue) {
        if (topicQueue.getNameBaseOnQueueName()) {
            return getRouteKey(topicQueue.getQueueName());
        } else {
            return topicQueue.getRouteKey();
        }
    }

    public String getTopicDlxExchangeName(MyRabbitMqProperties.TopicQueue topicQueue) {
        if (topicQueue.getNameBaseOnQueueName()) {
            return getDlxExchangeName(topicQueue.getQueueName(), ExchangeType.DIRECT);
        } else {
            return topicQueue.getDlxExchangeName();
        }
    }

    public String getTopicDlxRouteKey(MyRabbitMqProperties.TopicQueue topicQueue) {
        if (topicQueue.getNameBaseOnQueueName()) {
            return getDlxRouteKey(topicQueue.getQueueName());
        } else {
            return topicQueue.getDlxRouteKey();
        }
    }

    public String getDelayedExchangeName(MyRabbitMqProperties.DelayedQueue delayedQueue) {
        if (delayedQueue.getNameBaseOnQueueName()) {
            return getExchangeName(delayedQueue.getQueueName(), ExchangeType.DELAYED);
        } else {
            return delayedQueue.getExchangeName();
        }
    }

    public String getDelayedRouteKey(MyRabbitMqProperties.DelayedQueue delayedQueue) {
        if (delayedQueue.getNameBaseOnQueueName()) {
            return getRouteKey(delayedQueue.getQueueName());
        } else {
            return delayedQueue.getRouteKey();
        }
    }

    private String getExchangeName(String queueName, ExchangeType exchangeType) {
        String replace = StrUtil.replace(queueName, Strings.DASH, Strings.DOT);
        return replace + RabbitMq.EXCHANGE_SUB_PREFIX + exchangeType.getSubPrefix();
    }

    private String getRouteKey(String queueName) {
        return StrUtil.replace(queueName, Strings.DASH, Strings.DOT);
    }

    private String getDlxExchangeName(String queueName, ExchangeType exchangeType) {
        return getExchangeName(queueName, exchangeType) + RabbitMq.DLX_SUB_PREFIX;
    }

    private String getDlxRouteKey(String queueName) {
        return getRouteKey(queueName) + RabbitMq.DLX_SUB_PREFIX;
    }
}
