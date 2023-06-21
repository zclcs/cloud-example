package com.zclcs.common.redis.starter.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 字典缓存工厂配置
 *
 * @author zclcs
 * @since 1.4.2
 */
@Getter
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class DictCacheFactory {

    public <K1, V1> Cache<K1, V1> build(int maximumSize, int initialCapacity, Duration duration) {
        final Caffeine<Object, Object> builder = Caffeine.newBuilder();
        builder
                .maximumSize(maximumSize)
                .initialCapacity(initialCapacity)
                .expireAfterWrite(duration);

        return builder.build();
    }
}
