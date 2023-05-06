package com.zclcs.cloud.lib.rabbit.mq.utils;

import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.RabbitMq;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.enums.ExchangeType;
import lombok.experimental.UtilityClass;

/**
 * @author zclcs
 */
@UtilityClass
public class RabbitKeyUtil {

    public String getExchangeName(String queueName, ExchangeType exchangeType) {
        String replace = StrUtil.replace(queueName, Strings.DASH, Strings.DOT);
        return replace + RabbitMq.EXCHANGE_SUB_PREFIX + exchangeType.getSubPrefix();
    }

    public String getRouteKey(String queueName) {
        return StrUtil.replace(queueName, Strings.DASH, Strings.DOT);
    }

    public String getDlxExchangeName(String queueName, ExchangeType exchangeType) {
        return getExchangeName(queueName, exchangeType) + RabbitMq.DLX_SUB_PREFIX;
    }

    public String getDlxRouteKey(String queueName) {
        return getRouteKey(queueName) + RabbitMq.DLX_SUB_PREFIX;
    }
}
