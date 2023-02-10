package com.zclcs.platform.gateway.filter;

import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.platform.gateway.service.RouteEnhanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * @author zclcs
 */
@Slf4j
@Order(0)
@RequiredArgsConstructor
public class MyGatewayRequestFilter implements GlobalFilter {

    private final RouteEnhanceService routeEnhanceService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Mono<Void> blackListResult = routeEnhanceService.filterBlackList(exchange);
        if (blackListResult != null) {
            routeEnhanceService.saveBlockLogs(exchange);
            return blackListResult;
        }
        Mono<Void> rateLimitResult = routeEnhanceService.filterRateLimit(exchange);
        if (rateLimitResult != null) {
            routeEnhanceService.saveRateLimitLogs(exchange);
            return rateLimitResult;
        }
        routeEnhanceService.saveRequestLogs(exchange);

        // 1. 清洗请求头中from 参数
        ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders -> {
            httpHeaders.remove(SecurityConstant.FROM);
            // 设置请求时间
            httpHeaders.put(MyConstant.REQUEST_START_TIME,
                    Collections.singletonList(String.valueOf(System.currentTimeMillis())));
        }).build();
        ServerWebExchange newExchange = exchange.mutate().request(request).build();
        return chain.filter(newExchange);
    }
}
