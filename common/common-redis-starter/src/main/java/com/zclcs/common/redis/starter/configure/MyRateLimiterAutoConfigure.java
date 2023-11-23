package com.zclcs.common.redis.starter.configure;

import com.zclcs.common.redis.starter.rate.limiter.impl.DefaultRateLimiterClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 基于 redis 的分布式限流自动配置
 *
 * @author L.cm
 */
@AutoConfiguration
@ConditionalOnProperty(value = "my.rate.limiter.enable", havingValue = "true", matchIfMissing = true)
public class MyRateLimiterAutoConfigure {

    private RedisScript<Long> redisRateLimiterScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("META-INF/scripts/rate_limiter.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultRateLimiterClient defaultRateLimiterClient(RedisTemplate<String, Object> redisTemplate) {
        RedisScript<Long> redisRateLimiterScript = redisRateLimiterScript();
        return new DefaultRateLimiterClient(redisTemplate, redisRateLimiterScript);
    }
}
