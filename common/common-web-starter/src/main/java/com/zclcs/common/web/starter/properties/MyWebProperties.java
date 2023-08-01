package com.zclcs.common.web.starter.properties;

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
@ConfigurationProperties(prefix = "my.web")
public class MyWebProperties {

    private Boolean enableDateConverter = true;

    public Boolean getEnableDateConverter() {
        return enableDateConverter;
    }

    public void setEnableDateConverter(Boolean enableDateConverter) {
        this.enableDateConverter = enableDateConverter;
    }

}
