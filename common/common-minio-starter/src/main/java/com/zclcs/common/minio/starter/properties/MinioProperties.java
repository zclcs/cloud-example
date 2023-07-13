package com.zclcs.common.minio.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
@RefreshScope
public class MinioProperties {

    /**
     * ip地址
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * 连接地址
     */
    private String endpoint;

    /**
     * 用户名
     */
    private String rootUser;

    /**
     * 密码
     */
    private String rootPassword;

    /**
     * 域名
     */
    private String domainName;

}
