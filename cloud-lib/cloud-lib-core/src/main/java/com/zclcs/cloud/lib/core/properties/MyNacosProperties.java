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
@ConfigurationProperties(prefix = "spring.cloud.nacos.config")
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
