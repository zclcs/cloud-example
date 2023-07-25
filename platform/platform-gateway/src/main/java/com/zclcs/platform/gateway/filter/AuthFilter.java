package com.zclcs.platform.gateway.filter;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.gateway.properties.GatewayConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * Sa-Token 拦截器
 *
 * @author zclcs
 */
@Configuration
public class AuthFilter {

    /**
     * 注册 Sa-Token 全局过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter(GatewayConfigProperties gatewayConfigProperties, ObjectMapper objectMapper) {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")
                .addExclude("/favicon.ico", "/webjars/js/**", "/webjars/css/**", "/doc.html", "/**/v3/api-docs/**", "/actuator/**")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由
                    SaRouter.match("/**").notMatch(gatewayConfigProperties.getIgnoreUrls()).check(r -> {
                        // 检查是否登录 是否有token
                        StpUtil.checkLogin();
                    });
                }).setError(e -> {
                    SaHolder.getResponse().setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    SaHolder.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
                    try {
                        return objectMapper.writeValueAsString(RspUtil.message("认证失败"));
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

}
