package com.zclcs.cloud.lib.core.constant;

/**
 * @author zclcs
 */
public interface RabbitMq {

    /**
     * 队列名称交换机后缀
     */
    String EXCHANGE_SUB_PREFIX = ".exchange";

    /**
     * 死信后缀
     */
    String DLX_SUB_PREFIX = ".dlx";

    String SYSTEM_LOG = "systemLog";

    String SYSTEM_LOGIN_LOG = "systemLoginLog";

    String SYSTEM_ROUTE_LOG = "systemRouteLog";

    String SYSTEM_BLOCK_LOG = "systemBlockLog";

    String SYSTEM_RATE_LIMIT_LOG = "systemRateLimitLog";
}
