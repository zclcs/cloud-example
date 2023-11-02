package com.zclcs.cloud.lib.security.lite.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zclcs
 * 资源服务器对外直接暴露URL,如果设置contex-path 要特殊处理
 */
@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = "my.security")
public class MySecurityProperties implements InitializingBean {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private List<String> ignoreUrls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
//        RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
//        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
//
//        map.keySet().forEach(info -> {
//            HandlerMethod handlerMethod = map.get(info);
//
//            // 获取方法上边的注解 替代path variable 为 *
//            Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
//            Optional.ofNullable(method).ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
//                    .getPatternValues().forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
//
//            // 获取类上边的注解, 替代path variable 为 *
//            Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
//            Optional.ofNullable(controller).ifPres ent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
//                    .getPatternValues().forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
//        });
    }

}
