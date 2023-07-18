package com.zclcs.cloud.lib.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Component
@ConfigurationProperties(prefix = "my")
public class GlobalProperties {

    /**
     * 服务缓存前缀
     */
    private String redisCachePrefix;

    /**
     * 默认密码
     */
    private String defaultPassword;

    public String getRedisCachePrefix() {
        return redisCachePrefix;
    }

    public void setRedisCachePrefix(String redisCachePrefix) {
        this.redisCachePrefix = redisCachePrefix;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    @Override
    public String toString() {
        return "GlobalProperties{" +
                "redisCachePrefix='" + redisCachePrefix + '\'' +
                ", defaultPassword='" + defaultPassword + '\'' +
                '}';
    }
}
