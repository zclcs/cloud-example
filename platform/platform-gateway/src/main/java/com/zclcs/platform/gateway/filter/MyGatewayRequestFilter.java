package com.zclcs.platform.gateway.filter;

import cn.dev33.satoken.same.SaSameUtil;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.platform.gateway.service.RouteEnhanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zclcs
 */
@Slf4j
@Component
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
//        Mono<Void> rateLimitResult = routeEnhanceService.filterRateLimit(exchange);
//        if (rateLimitResult != null) {
//            routeEnhanceService.saveRateLimitLogs(exchange);
//            return rateLimitResult;
//        }
        // 1. 清洗请求头中from 参数 添加
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(httpHeaders -> {
                            httpHeaders.remove(Security.FROM);
                            httpHeaders.remove(SaSameUtil.SAME_TOKEN);
                            httpHeaders.add(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken());
                        }
                ).build();
        ServerWebExchange newExchange = exchange.mutate().request(request).build();
        newExchange.getAttributes().put(CommonCore.START_TIME, System.currentTimeMillis());
        return chain.filter(newExchange).then(Mono.fromRunnable(() ->
                routeEnhanceService.saveRequestLogs(exchange)));
    }
}
