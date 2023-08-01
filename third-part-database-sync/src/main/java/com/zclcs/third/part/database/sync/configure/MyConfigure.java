package com.zclcs.third.part.database.sync.configure;

import com.zclcs.third.part.database.sync.properties.ThirdPartDatabaseSyncProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zclcs
 */
@Configuration
@EnableConfigurationProperties(ThirdPartDatabaseSyncProperties.class)
public class MyConfigure {
}
