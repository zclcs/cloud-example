package com.zclcs.common.core.utils;

import com.zclcs.common.core.properties.GlobalProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Component
@RequiredArgsConstructor
public class RabbitMqUtil {

    public static String NACOS_NAMESPACE;
    private final GlobalProperties globalProperties;

    @PostConstruct
    public void init() {
        NACOS_NAMESPACE = globalProperties.getNacosNamespace();
    }
}
