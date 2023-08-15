package com.zclcs.cloud.lib.core.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author zclcs
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = "spring.rabbitmq")
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
