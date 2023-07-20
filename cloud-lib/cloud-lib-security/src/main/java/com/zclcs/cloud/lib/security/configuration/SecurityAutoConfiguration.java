package com.zclcs.cloud.lib.security.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * ip2region 自动化配置
 *
 * @author zclcs
 */
@AutoConfiguration
@ImportRuntimeHints(SecurityRuntimeHintsRegistrar.class)
public class SecurityAutoConfiguration {

}
