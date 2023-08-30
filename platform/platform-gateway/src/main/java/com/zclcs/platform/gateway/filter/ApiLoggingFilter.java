package com.zclcs.platform.gateway.filter;

import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.platform.gateway.service.RouteEnhanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhangran
 * @since 2021/7/13
 * <p>
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求的API调用过滤，记录接口的请求时间，方便日志审计、告警、分析等运维操作 2. 后期可以扩展对接其他日志系统
 * <p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiLoggingFilter implements GlobalFilter, Ordered {

    private final RouteEnhanceService routeEnhanceService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(CommonCore.START_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> routeEnhanceService.saveRequestLogs(exchange)));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
