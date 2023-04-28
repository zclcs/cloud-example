package com.zclcs.common.aop.configure;

import com.zclcs.common.aop.aspect.ControllerLogAspect;
import com.zclcs.common.aop.properties.MyAopProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zclcs
 */
@AutoConfiguration
@EnableConfigurationProperties(MyAopProperties.class)
public class MyAopAutoConfigure {

    @ConditionalOnProperty(name = "my.aop.enable-log-for-controller", havingValue = "true")
    @Bean
    public ControllerLogAspect controllerLogAspect() {
        return new ControllerLogAspect();
    }
}
