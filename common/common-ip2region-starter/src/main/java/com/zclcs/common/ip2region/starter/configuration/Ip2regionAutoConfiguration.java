package com.zclcs.common.ip2region.starter.configuration;

import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.common.ip2region.starter.impl.Ip2regionSearcherImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ResourceLoader;

/**
 * ip2region 自动化配置
 *
 * @author zclcs
 */
@AutoConfiguration
@ImportRuntimeHints(Ip2regionRuntimeHintsRegistrar.class)
@EnableConfigurationProperties(Ip2regionProperties.class)
public class Ip2regionAutoConfiguration {

    @Bean
    public Ip2regionSearcher ip2regionSearcher(ResourceLoader resourceLoader,
                                               Ip2regionProperties properties) {
        return new Ip2regionSearcherImpl(resourceLoader, properties);
    }

}
