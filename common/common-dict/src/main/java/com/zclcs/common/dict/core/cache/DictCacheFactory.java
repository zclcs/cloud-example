package com.zclcs.common.dict.core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zclcs.common.dict.core.properties.DictProperties;
import com.zclcs.common.dict.core.properties.DictPropertiesCache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 字典缓存工厂配置
 *
 * @author zclcs
 */
@Getter
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class DictCacheFactory {
    private final DictProperties dictProperties;
    private final List<DictCacheCustomizer> cacheCustomizers;

    public <K1, V1> Cache<K1, V1> build() {
        final DictPropertiesCache propertiesCache = dictProperties.getCache();
        if (!propertiesCache.isEnabled()) {
            return null;
        }
        final Caffeine<Object, Object> builder = Caffeine.newBuilder();
        builder
                .expireAfterWrite(propertiesCache.getDuration())
                .maximumSize(propertiesCache.getMaximumSize())
                .initialCapacity(propertiesCache.getInitialCapacity());

        for (final DictCacheCustomizer customizer : cacheCustomizers) {
            customizer.customize(builder);
        }

        return builder.build();
    }
}
