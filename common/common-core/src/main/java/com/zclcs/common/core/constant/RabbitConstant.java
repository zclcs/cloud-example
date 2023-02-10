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

    String SYSTEM_LOG_QUEUE = "queue.system.log";

    String SYSTEM_LOGIN_LOG_QUEUE = "queue.system.login.log";

    String SYSTEM_ROUTE_LOG_QUEUE = "queue.system.route.log";

    String SYSTEM_BLOCK_LOG_QUEUE = "queue.system.block.log";

    String SYSTEM_RATE_LIMIT_LOG_QUEUE = "queue.system.rate.limit.log";

    String SYSTEM_LOG_ROUTE_KEY = "system-log-routing-key";

    String SYSTEM_LOGIN_LOG_ROUTE_KEY = "system-login-log-routing-key";

    String SYSTEM_ROUTE_LOG_ROUTE_KEY = "system-route-log-routing-key";

    String SYSTEM_BLOCK_LOG_LOG_ROUTE_KEY = "system-block-log-routing-key";

    String SYSTEM_RATE_LIMIT_LOG_ROUTE_KEY = "system-rate-limit-log-routing-key";

    String DELAY_QUEUE = "delay.queue";

    String DELAY_MODE_QUEUE = "delay.mode";

}
