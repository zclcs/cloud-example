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
@ConfigurationProperties(prefix = "spring.rabbitmq")
@RefreshScope
public class RabbitmqApiInfoProperties {

    /**
     * ip
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * open api 端口
     */
    private String apiPort;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 类似于命名空间
     */
    private String virtualHost;
}
