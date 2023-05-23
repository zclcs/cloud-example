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
@ConfigurationProperties(prefix = "my")
@RefreshScope
public class GlobalProperties {

    /**
     * 服务缓存前缀
     */
    private String redisCachePrefix;

    /**
     * 默认密码
     */
    private String defaultPassword;
}