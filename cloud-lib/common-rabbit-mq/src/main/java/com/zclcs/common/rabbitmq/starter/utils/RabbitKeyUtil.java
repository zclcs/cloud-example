package com.zclcs.common.rabbitmq.starter.utils;

import cn.hutool.core.util.StrUtil;
import com.zclcs.common.core.constant.RabbitMq;
import com.zclcs.common.core.constant.Strings;
import com.zclcs.common.core.enums.ExchangeType;
import lombok.experimental.UtilityClass;

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
