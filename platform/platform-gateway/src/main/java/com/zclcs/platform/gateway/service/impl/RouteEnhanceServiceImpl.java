package com.zclcs.platform.gateway.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Stopwatch;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.constant.Params;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.core.utils.SpringContextHolderUtil;
import com.zclcs.platform.gateway.event.SaveBlockLogEvent;
import com.zclcs.platform.gateway.event.SaveRateLimitLogEvent;
import com.zclcs.platform.gateway.event.SaveRouteLogEvent;
import com.zclcs.platform.gateway.service.RouteEnhanceCacheService;
import com.zclcs.platform.gateway.service.RouteEnhanceService;
import com.zclcs.platform.gateway.utils.GatewayUtil;
import com.zclcs.platform.system.api.bean.ao.BlockLogAo;
import com.zclcs.platform.system.api.bean.ao.RateLimitLogAo;
import com.zclcs.platform.system.api.bean.ao.RouteLogAo;
import com.zclcs.platform.system.api.bean.cache.BlackListCacheBean;
import com.zclcs.platform.system.api.bean.cache.RateLimitRuleCacheBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zclcs
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RouteEnhanceServiceImpl implements RouteEnhanceService {

    private static final String METHOD_ALL = "ALL";
    private final RouteEnhanceCacheService routeEnhanceCacheService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filterBlackList(ServerWebExchange exchange) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        try {
            URI originUri = GatewayUtil.getGatewayOriginalRequestUrl(exchange);
            if (originUri != null) {
                String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
                HttpMethod httpMethod = request.getMethod();
                String requestMethod = httpMethod.name();
                AtomicBoolean forbid = new AtomicBoolean(false);
                Set<BlackListCacheBean> blackList = routeEnhanceCacheService.getBlackList(requestIp);
                blackList.addAll(routeEnhanceCacheService.getBlackList());

                doBlackListCheck(forbid, blackList, originUri, requestMethod);

                log.debug("Blacklist verification completed - {}", stopwatch.stop());
                if (forbid.get()) {
                    return GatewayUtil.makeWebFluxResponse(response, MediaType.APPLICATION_JSON_VALUE,
                            HttpStatus.NOT_ACCEPTABLE, RspUtil.message("黑名单限制，禁止访问"));
                }
            } else {
                log.debug("Request IP not obtained, no blacklist check - {}", stopwatch.stop());
            }
        } catch (Exception e) {
            log.warn("Blacklist verification failed : {} - {}", e.getMessage(), stopwatch.stop());
        }
        return null;
    }

    @Override
    public Mono<Void> filterRateLimit(ServerWebExchange exchange) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        try {
            URI originUri = GatewayUtil.getGatewayOriginalRequestUrl(exchange);
            if (originUri != null) {
                String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
                HttpMethod httpMethod = request.getMethod();
                String requestMethod = httpMethod.name();
                AtomicBoolean limit = new AtomicBoolean(false);
                RateLimitRuleCacheBean rule = routeEnhanceCacheService.getRateLimitRule(originUri.getPath(), METHOD_ALL);
                if (rule == null) {
                    rule = routeEnhanceCacheService.getRateLimitRule(originUri.getPath(), requestMethod);
                }
                if (rule != null) {
                    Mono<Void> result = doRateLimitCheck(limit, rule, originUri, requestIp, requestMethod, response);
                    log.debug("Rate limit verification completed - {}", stopwatch.stop());
                    if (result != null) {
                        return result;
                    }
                }
            } else {
                log.debug("Request IP not obtained, no rate limit filter - {}", stopwatch.stop());
            }
        } catch (Exception e) {
            log.warn("Current limit failure : {} - {}", e.getMessage(), stopwatch.stop());
        }
        return null;
    }

    @Override
    public void saveRequestLogs(ServerWebExchange exchange) {
        Long startTime = exchange.getAttribute(CommonCore.START_TIME);
        if (startTime != null) {
            URI originUri = GatewayUtil.getGatewayOriginalRequestUrl(exchange);
            Long executeTime = (System.currentTimeMillis() - startTime);
            int code = 500;
            if (exchange.getResponse().getStatusCode() != null) {
                code = exchange.getResponse().getStatusCode().value();
            }
            URI url = GatewayUtil.getGatewayRequestUrl(exchange);
            Route route = GatewayUtil.getGatewayRoute(exchange);
            ServerHttpRequest request = exchange.getRequest();
            String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
            if (url != null && route != null) {
                HttpMethod httpMethod = request.getMethod();
                String requestMethod = httpMethod.name();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime requestTime = Instant.ofEpochMilli(startTime).atZone(zoneId).toLocalDateTime();
                RouteLogAo routeLog = RouteLogAo.builder()
                        .routeIp(requestIp)
                        .requestUri(originUri.getPath())
                        .targetServer(route.getId())
                        .targetUri(url.getPath())
                        .requestMethod(requestMethod)
                        .requestTime(requestTime)
                        .code(String.valueOf(code))
                        .time(executeTime)
                        .build();
                SpringContextHolderUtil.publishEvent(new SaveRouteLogEvent(routeLog));
            }
            // 记录日志
            if (log.isDebugEnabled()) {
                log.debug("来自IP地址：{}的请求接口：{}，响应状态码：{}，请求耗时：{}ms", requestIp, originUri.getPath(), code, executeTime);
            }
        }
    }

    @Override
    public void saveBlockLogs(ServerWebExchange exchange) {
        URI originUri = GatewayUtil.getGatewayOriginalRequestUrl(exchange);
        ServerHttpRequest request = exchange.getRequest();
        String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
        if (originUri != null) {
            HttpMethod httpMethod = request.getMethod();
            String requestMethod = httpMethod.name();
            BlockLogAo blockLog = BlockLogAo.builder()
                    .blockIp(requestIp)
                    .requestMethod(requestMethod)
                    .requestUri(originUri.getPath())
                    .requestTime(LocalDateTime.now())
                    .build();
            SpringContextHolderUtil.publishEvent(new SaveBlockLogEvent(blockLog));
            log.debug("Store blocked request logs >>>");
        }
    }

    @Override
    public void saveRateLimitLogs(ServerWebExchange exchange) {
        URI originUri = GatewayUtil.getGatewayOriginalRequestUrl(exchange);
        ServerHttpRequest request = exchange.getRequest();
        String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
        if (originUri != null) {
            HttpMethod httpMethod = request.getMethod();
            String requestMethod = httpMethod.name();
            RateLimitLogAo rateLimitLog = RateLimitLogAo.builder()
                    .rateLimitLogIp(requestIp)
                    .requestMethod(requestMethod)
                    .requestUri(originUri.getPath())
                    .requestTime(LocalDateTime.now())
                    .build();
            SpringContextHolderUtil.publishEvent(new SaveRateLimitLogEvent(rateLimitLog));
            log.debug("Store rate limit logs >>>");
        }
    }

    private void doBlackListCheck(AtomicBoolean forbid, Set<BlackListCacheBean> blackList, URI uri, String requestMethod) {
        for (BlackListCacheBean b : blackList) {
            if (pathMatcher.match(b.getRequestUri(), uri.getPath()) && Dict.OPEN.equals(b.getBlackStatus())) {
                if (Params.METHOD_ALL.equalsIgnoreCase(b.getRequestMethod())
                        || StrUtil.equalsIgnoreCase(requestMethod, b.getRequestMethod())) {
                    checkTime(forbid, b.getLimitFrom(), b.getLimitTo());
                }
            }
            if (forbid.get()) {
                break;
            }
        }
    }

    private Mono<Void> doRateLimitCheck(AtomicBoolean limit, RateLimitRuleCacheBean rule, URI uri,
                                        String requestIp, String requestMethod, ServerHttpResponse response) {
        boolean isRateLimitRuleHit = Dict.OPEN.equals(rule.getRuleStatus())
                && (Params.METHOD_ALL.equalsIgnoreCase(rule.getRequestMethod())
                || StrUtil.equalsIgnoreCase(requestMethod, rule.getRequestMethod()));
        if (isRateLimitRuleHit) {
            checkTime(limit, rule.getLimitFrom(), rule.getLimitTo());
        }
        if (limit.get()) {
            String requestUri = uri.getPath();
            int count = routeEnhanceCacheService.getCurrentRequestCount(requestUri, requestIp);
            if (count == 0) {
                routeEnhanceCacheService.setCurrentRequestCount(requestUri, requestIp, Long.parseLong(rule.getIntervalSec()));
            } else if (count >= rule.getRateLimitCount()) {
                return GatewayUtil.makeWebFluxResponse(response, MediaType.APPLICATION_JSON_VALUE,
                        HttpStatus.TOO_MANY_REQUESTS, RspUtil.message("访问频率超限，请稍后再试"));
            } else {
                routeEnhanceCacheService.incrCurrentRequestCount(requestUri, requestIp);
            }
        }
        return null;
    }

    private void checkTime(AtomicBoolean checkTime, String limitFrom, String limitTo) {
        if (StrUtil.isNotBlank(limitFrom) && StrUtil.isNotBlank(limitTo)) {
            DateTime now = DateUtil.date();
            DateTime limitFromTime = DateUtil.parse(now.toString(DatePattern.NORM_DATE_PATTERN) + StrUtil.SPACE + limitFrom, DatePattern.NORM_DATETIME_PATTERN);
            DateTime limitToTime = DateUtil.parse(now.toString(DatePattern.NORM_DATE_PATTERN) + StrUtil.SPACE + limitTo, DatePattern.NORM_DATETIME_PATTERN);
            if (DateUtil.isIn(now, limitFromTime, limitToTime)) {
                checkTime.set(true);
            }
        } else {
            checkTime.set(true);
        }
    }
}
