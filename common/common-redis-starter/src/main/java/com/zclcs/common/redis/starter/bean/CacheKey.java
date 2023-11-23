package com.zclcs.common.redis.starter.bean;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Duration;

/**
 * cache key 封装
 *
 * @author L.cm
 */
@Getter
@ToString
@RequiredArgsConstructor
public class CacheKey {

    /**
     * redis key
     */
    private final String key;

    /**
     * 超时时间 秒
     */
    @Nullable
    private final Duration expire;

    public CacheKey(String key) {
        this(key, null);
    }

}
