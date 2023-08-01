package com.zclcs.common.jackson.starter.properties;

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
@ConfigurationProperties(prefix = "my.jackson")
public class MyJacksonProperties {

    private Boolean enable = true;

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

}
