package com.zclcs.common.logging.starter.configure;

import com.zclcs.common.aop.starter.aspect.ControllerLogAspect;
import com.zclcs.common.logging.starter.properties.MyLogProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zclcs
 */
@AutoConfiguration
@EnableConfigurationProperties(MyLogProperties.class)
public class MyLogAutoConfigure {

    @ConditionalOnProperty(name = "my.log.enable-log-for-controller", havingValue = "true")
    @Bean
    public ControllerLogAspect controllerLogAspect() {
        return new ControllerLogAspect();
    }
}
