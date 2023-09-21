package com.zclcs.common.redis.starter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zclcs
 */
@Getter
@AllArgsConstructor
public enum CacheType {

    /**
     * 直接缓存null对象
     */
    CACHE_NULL(1, "直接缓存null对象"),
    /**
     * 利用布隆过滤器
     */
//    CACHE_USING_BLOOM_FILTER(2, "使用布隆过滤器"),
    ;

    private final Integer value;
    private final String title;
}
