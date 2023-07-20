package com.zclcs.common.validation.starter.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * ip2region 自动化配置
 *
 * @author zclcs
 */
@AutoConfiguration
@ImportRuntimeHints(ValidationRuntimeHintsRegistrar.class)
public class ValidationAutoConfiguration {

}
