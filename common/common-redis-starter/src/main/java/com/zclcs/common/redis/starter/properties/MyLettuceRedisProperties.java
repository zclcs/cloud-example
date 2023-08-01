package com.zclcs.common.redis.starter.properties;

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

}
