package com.zclcs.common.jackson.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Component
@ConfigurationProperties(prefix = "my.jackson")
public class MyJacksonProperties {

    private Boolean enable = true;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "MyJacksonProperties{" +
                "enable=" + enable +
                '}';
    }
}
