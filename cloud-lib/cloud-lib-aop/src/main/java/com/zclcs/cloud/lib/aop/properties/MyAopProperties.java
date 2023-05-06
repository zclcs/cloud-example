package com.zclcs.cloud.lib.aop.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zclcs
 */
@ConfigurationProperties(prefix = "my.aop")
public class MyAopProperties {

    /**
     * 是否开启controller层api调用的日志
     */
    private Boolean enableLogForController = true;

    public Boolean getEnableLogForController() {
        return enableLogForController;
    }

    public void setEnableLogForController(Boolean enableLogForController) {
        this.enableLogForController = enableLogForController;
    }

}
