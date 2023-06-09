package com.zclcs.platform.gateway.configure;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.filter.*;
import com.zclcs.platform.gateway.handler.FlowsHandler;
import com.zclcs.platform.gateway.handler.ImageCodeHandler;
import com.zclcs.platform.gateway.handler.MyGatewayExceptionHandler;
import com.zclcs.platform.gateway.handler.RoutesHandler;
import com.zclcs.platform.gateway.properties.GatewayConfigProperties;
import com.zclcs.platform.gateway.properties.MyValidateCodeProperties;
import com.zclcs.platform.gateway.service.RouteEnhanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 网关配置
 *
 * @author L.cm
 */
@EnableAsync
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({GatewayConfigProperties.class, MyValidateCodeProperties.class})
@RequiredArgsConstructor
public class GatewayConfiguration {

    private final ServerProperties serverProperties;
    private final ApplicationContext applicationContext;
    private final WebProperties webProperties;
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                             ServerCodecConfigurer serverCodecConfigurer) {

        MyGatewayExceptionHandler myGatewayExceptionHandler = new MyGatewayExceptionHandler();
        myGatewayExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        myGatewayExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        myGatewayExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return myGatewayExceptionHandler;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider) {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolversProvider.getIfAvailable(Collections::emptyList), serverCodecConfigurer);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @Bean
    public PasswordDecoderFilter passwordDecoderFilter(GatewayConfigProperties configProperties) {
        return new PasswordDecoderFilter(configProperties);
    }

    @Bean
    public MyGatewayRequestFilter myGatewayRequestFilter() {
        return new MyGatewayRequestFilter();
    }

    @Bean
    public ApiLoggingFilter apiLoggingFilter(RouteEnhanceService routeEnhanceService) {
        return new ApiLoggingFilter(routeEnhanceService);
    }

    @Bean
    public RequestRateLimiterFilter requestRateLimiterFilter(RouteEnhanceService routeEnhanceService) {
        return new RequestRateLimiterFilter(routeEnhanceService);
    }

    @Bean
    public ValidateCodeGatewayFilter validateCodeGatewayFilter(GatewayConfigProperties configProperties,
                                                               ObjectMapper objectMapper, RedisService redisService) {
        return new ValidateCodeGatewayFilter(configProperties, objectMapper, redisService);
    }

    @Bean
    public ImageCodeHandler imageCodeHandler(RedisService redisService, MyValidateCodeProperties myValidateCodeProperties) {
        return new ImageCodeHandler(redisService, myValidateCodeProperties);
    }

    @Bean(CommonCore.ASYNC_POOL)
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("My-Gateway-Async-Thread");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public RoutesHandler routesHandler(ObjectMapper objectMapper,
                                       RouteDefinitionWriter routeDefinitionWriter) {
        return new RoutesHandler(objectMapper,
                routeDefinitionWriter);
    }

    @Bean
    public FlowsHandler flowsHandler(ObjectMapper objectMapper) {
        return new FlowsHandler(objectMapper);
    }

    @Profile({"dev", "test"})
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

}
