package com.zclcs.common.feign.starter.sentinel;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.zclcs.common.feign.starter.sentinel.ext.MySentinelFeign;
import com.zclcs.common.feign.starter.sentinel.handler.MyUrlBlockHandler;
import com.zclcs.common.feign.starter.sentinel.parser.MyHeaderRequestOriginParser;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author lengleng
 * @date 2020-02-12
 * <p>
 * sentinel 配置
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class SentinelAutoConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    public Feign.Builder feignSentinelBuilder() {
        return MySentinelFeign.builder();
    }

    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler() {
        return new MyUrlBlockHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestOriginParser requestOriginParser() {
        return new MyHeaderRequestOriginParser();
    }

}
