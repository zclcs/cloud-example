package com.zclcs.cloud.lib.core.configure;

import com.zclcs.cloud.lib.core.properties.GlobalProperties;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.cloud.lib.core.properties.RabbitmqApiInfoProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author zclcs
 */
@AutoConfiguration
@EnableConfigurationProperties(value = {MyNacosProperties.class, GlobalProperties.class, RabbitmqApiInfoProperties.class})
public class MyCoreAutoConfigure {
}
