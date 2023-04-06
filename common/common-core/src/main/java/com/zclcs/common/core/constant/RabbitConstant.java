package com.zclcs.common.core.constant;

/**
 * <p>
 * RabbitMQ常量池
 * </p>
 *
 * @author zclcs
 */
public interface RabbitConstant {

    String DLX_EXCHANGE_PRE = "x-dead-letter-exchange";

    String DLX_ROUTE_KEY_PRE = "x-dead-letter-routing-key";

    String DIRECT_EXCHANGE = "exchange.direct";

    String DLX_EXCHANGE = "exchange.dlx";

    String SYSTEM_LOG_QUEUE = "system-log";

    String SYSTEM_LOGIN_LOG_QUEUE = "system-login-log";

    String SYSTEM_ROUTE_LOG_QUEUE = "system-route-log";

    String SYSTEM_BLOCK_LOG_QUEUE = "system-block-log";

    String SYSTEM_RATE_LIMIT_LOG_QUEUE = "system-rate-limit-log";

    String SYSTEM_LOG_ROUTE_KEY = "system.log";

    String SYSTEM_LOGIN_LOG_ROUTE_KEY = "system.login.log";

    String SYSTEM_ROUTE_LOG_ROUTE_KEY = "system.route.log";

    String SYSTEM_BLOCK_LOG_LOG_ROUTE_KEY = "system.block.log";

    String SYSTEM_RATE_LIMIT_LOG_ROUTE_KEY = "system.rate.limit.log";

    String DELAY_QUEUE = "delay.queue";

    String DELAY_MODE_QUEUE = "delay.mode";

}
