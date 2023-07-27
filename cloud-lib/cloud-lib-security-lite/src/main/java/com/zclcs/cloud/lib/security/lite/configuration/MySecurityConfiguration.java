package com.zclcs.cloud.lib.security.lite.configuration;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.same.SaSameUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.security.lite.feign.MyOAuthRequestInterceptor;
import com.zclcs.cloud.lib.security.lite.properties.MySecurityProperties;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限安全配置
 *
 * @author zclcs
 */
@AutoConfiguration
@EnableConfigurationProperties(MySecurityProperties.class)
@RequiredArgsConstructor
public class MySecurityConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注解拦截器
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    /**
     * 校验是否从网关转发
     */
    @Bean
    public SaServletFilter getSaServletFilter(ObjectMapper objectMapper, MySecurityProperties mySecurityProperties) {
        return new SaServletFilter()
                .addInclude("/**")
                .addExclude("/actuator/**", "/error")
                // 每次请求都会进入
                .setAuth(obj -> SaRouter.match("/**").notMatch(mySecurityProperties.getIgnoreUrls()).check(SaSameUtil::checkCurrentRequestToken))
                .setError(e -> {
                    String message = "";
                    if (e instanceof NotLoginException exception) {
                        SaHolder.getResponse().setStatus(HttpStatus.FAILED_DEPENDENCY.value());
                        message = "token已过期";
                    } else {
                        SaHolder.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
                        message = "认证失败";
                    }
                    try {
                        return objectMapper.writeValueAsString(RspUtil.message(message));
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                ;
    }

    /**
     * 注入 oauth2 feign token 增强
     *
     * @return 拦截器
     */
    @Bean
    public RequestInterceptor oauthRequestInterceptor() {
        return new MyOAuthRequestInterceptor();
    }

}
