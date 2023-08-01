package com.zclcs.cloud.lib.aop.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author zclcs
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = "my.aop")
public class MyAopProperties {

    /**
     * 是否开启controller层api调用的日志
     */
    private Boolean enableLogForController = true;

    public void setEnableLogForController(Boolean enableLogForController) {
        this.enableLogForController = enableLogForController;
    }

}
