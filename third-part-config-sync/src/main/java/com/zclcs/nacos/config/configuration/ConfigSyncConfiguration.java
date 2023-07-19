package com.zclcs.nacos.config.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * ip2region 自动化配置
 *
 * @author zclcs
 */
@Configuration
@ImportRuntimeHints(ConfigSyncRuntimeHintsRegistrar.class)
public class ConfigSyncConfiguration {

}
