package com.zclcs.common.security.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.common.security.properties.PermitAllUrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @author zclcs
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class MyResourceServerAutoConfiguration {

    /**
     * 请求令牌的抽取逻辑
     *
     * @param urlProperties 对外暴露的接口列表
     * @return BearerTokenExtractor
     */
    @Bean
    public MyBearerTokenExtractor pigBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
        return new MyBearerTokenExtractor(urlProperties);
    }

    /**
     * 资源服务器异常处理
     *
     * @param objectMapper          jackson 输出对象
     * @param securityMessageSource 自定义国际化处理器
     * @return ResourceAuthExceptionEntryPoint
     */
    @Bean
    public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper,
                                                                           MessageSource securityMessageSource) {
        return new ResourceAuthExceptionEntryPoint(objectMapper, securityMessageSource);
    }

    /**
     * 资源服务器toke内省处理器
     *
     * @param authorizationService token 存储实现
     * @return TokenIntrospector
     */
    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
        return new MyCustomOpaqueTokenIntrospector(authorizationService);
    }

}
