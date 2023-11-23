package com.zclcs.common.redis.starter.rate.limiter.impl;

import com.zclcs.common.redis.starter.rate.limiter.RateLimiterClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zclcs
 */
@RequiredArgsConstructor
public class DefaultRateLimiterClient implements RateLimiterClient {

    /**
     * redis 限流 key 前缀
     */
    private static final String REDIS_KEY_PREFIX = "limiter:";

    /**
     * 失败的默认返回值
     */
    private static final long FAIL_CODE = 0;

    /**
     * redisTemplate
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * redisScript
     */
    private final RedisScript<Long> script;

    @Override
    public boolean isAllowed(String key, long max, long ttl, TimeUnit timeUnit) {
        // redis key
        String redisKeyBuilder = REDIS_KEY_PREFIX + key;
        List<String> keys = Collections.singletonList(redisKeyBuilder);
        // 转为毫秒
        long ttlMillis = timeUnit.toMillis(ttl);
        // 执行命令
        Long result = this.redisTemplate.execute(this.script, keys, Long.toString(max), Long.toString(ttlMillis));
        return result != null && result != FAIL_CODE;
    }
}
