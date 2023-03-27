package com.zclcs.common.dict.core.cache;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 字典缓存对象构造处理器
 *
 * @author zclcs
 */
public interface DictCacheCustomizer {
    /**
     * 处理方法
     *
     * @param caffeine 缓存对象构造
     */
    void customize(final Caffeine<Object, Object> caffeine);
}
