package com.zclcs.common.redis.starter.configure;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * @author zclcs
 */
public class MyStringRedisSerializer implements RedisSerializer<String> {

    /**
     * 字典表缓存前缀
     */
    private final String redisCachePrefix;

    public MyStringRedisSerializer(String redisCachePrefix) {
        this.redisCachePrefix = redisCachePrefix;
    }

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : StrUtil.addPrefixIfNot(new String(bytes, StandardCharsets.UTF_8), redisCachePrefix));
    }

    @Override
    public byte[] serialize(@Nullable String string) {
        return (string == null ? null : StrUtil.addPrefixIfNot(string, redisCachePrefix).getBytes(StandardCharsets.UTF_8));
    }

}
