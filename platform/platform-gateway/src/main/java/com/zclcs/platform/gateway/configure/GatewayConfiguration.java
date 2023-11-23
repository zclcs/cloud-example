package com.zclcs.platform.gateway.configure;

import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.RabbitMq;
import com.zclcs.cloud.lib.rabbit.mq.properties.MyRabbitMqProperties;
import com.zclcs.cloud.lib.rabbit.mq.service.RabbitService;
import com.zclcs.common.redis.starter.rate.limiter.impl.DefaultRateLimiterClient;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.event.SaveBlockLogEvent;
import com.zclcs.platform.gateway.event.SaveRateLimitLogEvent;
import com.zclcs.platform.gateway.event.SaveRouteLogEvent;
import com.zclcs.platform.gateway.filter.PasswordDecoderFilter;
import com.zclcs.platform.gateway.filter.ValidateCodeGatewayFilter;
import com.zclcs.platform.gateway.handler.ImageCodeHandler;
import com.zclcs.platform.gateway.handler.MyGatewayExceptionHandler;
import com.zclcs.platform.gateway.handler.RoutesHandler;
import com.zclcs.platform.gateway.properties.GatewayConfigProperties;
import com.zclcs.platform.gateway.properties.MyValidateCodeProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 网关配置
 *
 * @author zclcs
 */
@EnableAsync
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({GatewayConfigProperties.class, MyValidateCodeProperties.class})
@ImportRuntimeHints({GatewayRuntimeHintsRegistrar.class})
@RequiredArgsConstructor
@Slf4j
public class GatewayConfiguration {

    private final RabbitService rabbitService;
    private final MyRabbitMqProperties myRabbitMqProperties;

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
    public PasswordDecoderFilter passwordDecoderFilter(GatewayConfigProperties configProperties) {
        return new PasswordDecoderFilter(configProperties);
    }

    @Bean
    public ValidateCodeGatewayFilter validateCodeGatewayFilter(GatewayConfigProperties configProperties,
                                                               RedisService redisService) {
        return new ValidateCodeGatewayFilter(configProperties, redisService);
    }

    @Bean
    public ImageCodeHandler imageCodeHandler(RedisService redisService, MyValidateCodeProperties myValidateCodeProperties, DefaultRateLimiterClient defaultRateLimiterClient) {
        return new ImageCodeHandler(redisService, myValidateCodeProperties, defaultRateLimiterClient);
    }

    @Bean
    public RoutesHandler routesHandler(RouteDefinitionWriter routeDefinitionWriter) {
        return new RoutesHandler(routeDefinitionWriter);
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
        /*
          AbortPolicy:默认策略，丢弃任务，抛RejectedExecutionException异常
          DiscardPolicy:丢弃任务，但不抛异常
          DiscardOldestPolicy:丢弃队列最前面的任务，然后重新尝试执行任务
          CallerRunsPolicy:由调用线程处理任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 处理网关日志事件
     *
     * @param event SaveRouteLogEvent 事件
     */
    @Async(CommonCore.ASYNC_POOL)
    @EventListener
    public void saveRouteLogEventListener(final SaveRouteLogEvent event) {
        rabbitService.convertAndSend(myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_ROUTE_LOG), event.getSource());
    }

    /**
     * 处理网关日志事件
     *
     * @param event SaveRouteLogEvent 事件
     */
    @Async(CommonCore.ASYNC_POOL)
    @EventListener
    public void saveBlockLogEventListener(final SaveBlockLogEvent event) {
        rabbitService.convertAndSend(myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_BLOCK_LOG), event.getSource());
    }

    /**
     * 处理网关日志事件
     *
     * @param event SaveRouteLogEvent 事件
     */
    @Async(CommonCore.ASYNC_POOL)
    @EventListener
    public void saveRateLimitLogEventListener(final SaveRateLimitLogEvent event) {
        rabbitService.convertAndSend(myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_RATE_LIMIT_LOG), event.getSource());
    }

}
