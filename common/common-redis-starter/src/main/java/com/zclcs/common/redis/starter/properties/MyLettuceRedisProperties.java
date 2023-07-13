package com.zclcs.common.redis.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Component
@ConfigurationProperties(prefix = "my")
public class MyLettuceRedisProperties {

    /**
     * 缓存前缀
     */
    private String redisCachePrefix = "dev";

    private Boolean lettuceRedisEnable = true;

    public String getRedisCachePrefix() {
        return redisCachePrefix;
    }

    public void setRedisCachePrefix(String redisCachePrefix) {
        this.redisCachePrefix = redisCachePrefix;
    }

    public Boolean getLettuceRedisEnable() {
        return lettuceRedisEnable;
    }

    public void setLettuceRedisEnable(Boolean lettuceRedisEnable) {
        this.lettuceRedisEnable = lettuceRedisEnable;
    }

    @Override
    public String toString() {
        return "MyLettuceRedisProperties{" +
                "redisCachePrefix='" + redisCachePrefix + '\'' +
                ", lettuceRedisEnable=" + lettuceRedisEnable +
                '}';
    }
}
