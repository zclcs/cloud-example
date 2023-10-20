package com.zclcs.platform.maintenance.configure;

import com.zclcs.platform.maintenance.properties.PlatformMaintenanceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zclcs
 */
@Configuration
@EnableConfigurationProperties(PlatformMaintenanceProperties.class)
public class MyConfigure {
}
