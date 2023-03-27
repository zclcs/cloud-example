package com.zclcs.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zclcs
 */
@Getter
@AllArgsConstructor
public enum CacheType implements DictEnum<Integer> {

    /**
     * 直接缓存null对象
     */
    CACHE_NULL(1, "直接缓存null对象"),
    /**
     * 利用布隆过滤器
     */
    CACHE_USING_BLOOM_FILTER(2, "利用布隆过滤器"),
    ;

    private final Integer value;
    private final String title;

    @JsonCreator
    public static CacheType create(final Integer value) {
        return DictEnum.valueOf(values(), value);
    }
}
