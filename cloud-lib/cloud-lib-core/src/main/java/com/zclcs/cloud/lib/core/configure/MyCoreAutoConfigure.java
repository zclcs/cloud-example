package com.zclcs.cloud.lib.core.configure;

import com.zclcs.cloud.lib.core.properties.GlobalProperties;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.cloud.lib.core.properties.RabbitmqApiInfoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * @author zclcs
 */
@AutoConfiguration
@RequiredArgsConstructor
public class MyCoreAutoConfigure {

    private final MyNacosProperties myNacosProperties;

    private final GlobalProperties globalProperties;

    private final RabbitmqApiInfoProperties rabbitmqApiInfoProperties;
}
