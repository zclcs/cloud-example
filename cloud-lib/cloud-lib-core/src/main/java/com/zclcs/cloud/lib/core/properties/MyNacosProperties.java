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

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
