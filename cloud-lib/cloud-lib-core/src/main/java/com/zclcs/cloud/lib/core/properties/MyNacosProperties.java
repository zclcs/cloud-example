package com.zclcs.cloud.lib.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.cloud.nacos.config")
@RefreshScope
public class MyNacosProperties {

    /**
     * nacos endpoint
     */
    private String serverAddr;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 分组
     */
    private String group;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
