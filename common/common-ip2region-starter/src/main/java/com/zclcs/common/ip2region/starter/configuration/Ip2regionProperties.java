package com.zclcs.common.ip2region.starter.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * ip2region 配置类
 *
 * @author zclcs
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(Ip2regionProperties.PREFIX)
public class Ip2regionProperties {

    public static final String PREFIX = "my.ip2region";

    /**
     * ip2region.db 文件路径
     */
    private String dbFileLocation = "classpath:ip2region/ip2region.xdb";

    /**
     * ipv6wry.db 文件路径
     */
    private String ipv6dbFileLocation = "classpath:ip2region/ipv6wry.db";

}
