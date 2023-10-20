package com.zclcs.platform.maintenance.properties;

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
@ConfigurationProperties(prefix = "platform-maintenance")
public class PlatformMaintenanceProperties {

    /**
     * powerJob host
     */
    private String powerJobHost;

    /**
     * powerJob port
     */
    private String powerJobPort;

}
