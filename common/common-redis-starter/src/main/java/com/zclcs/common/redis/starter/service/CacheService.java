package com.zclcs.common.redis.starter.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import com.zclcs.common.core.enums.CacheType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author zclcs
 * 分布式、高并发业务场景缓存处理抽象类
 */
@Slf4j
@RequiredArgsConstructor
public abstract class CacheService<T> {

    private final RedisService redisService;
    private final String redisPrefix;
    private final CacheType cacheType;
    private final RBloomFilter<String> rBloomFilter;
    private final Long timeOut = 2L * 24L * 60L * 60L;

    /**
     * fegin 远程调用返回缓存对象方法
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    protected abstract T findByKey(Object... key);

    public void init(long expectedInsertions, double falseProbability) {
        rBloomFilter.tryInit(expectedInsertions, falseProbability);
    }

    /**
     * 缓存并获取缓存对象方法
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    @SneakyThrows
    public T cacheAndGetByKey(Object... key) {
        /**
         * 1 布隆过滤器
         * 1.1 存在一定量的误判 可以忽略不计（原理是hash冲突）
         * 1.2 布隆过滤器不支持删除（现在库中不存在 test2 用户，查询库中不存在后就加入布隆过滤器中 那后续 test2 又入库了 这里就一直返回不存在）
         * 1.3 解决方案
         * 1.3.1 只能使用表主键缓存 特性：新增之后不能改 数据新增后的key都是不一样的
         * 1.3.2 使用业务key缓存暂时没有解决办法（不管怎么定，业务key始终会有同样的key 会有误判的情况）
         * 2 直接缓存 null 对象
         * 2.1 存在攻击性的问题 一但遭受攻击 redis中会多很多不存在的key
         * 3 综合以上情况的解决方案
         * 3.1 缓存必须设备ttl时间
         * 3.2 最好使用表id做redis key 存储 (关联关系不能用表id) 一对多或者多对多
         * 3.3 如果必须用业务key存储建议缓存时直接缓存null 对象
         * 3.4 增删改都必须要删除对应的key
         */
        String redisKey = String.format(redisPrefix, key);
        Long redisTimeOut = timeOut + RandomUtil.randomLong(1, 360);
        T cache = this.findByKey(key);
        if (cacheType.equals(CacheType.CACHE_USING_BLOOM_FILTER)) {
            if (cache == null) {
                rBloomFilter.add(redisKey);
            } else if (cache instanceof Collection<?> collection) {
                if (CollectionUtil.isEmpty(collection)) {
                    rBloomFilter.add(redisKey);
                } else {
                    redisService.set(redisKey, cache, redisTimeOut);
                }
            } else {
                redisService.set(redisKey, cache, redisTimeOut);
            }
            return cache;
        } else if (cacheType.equals(CacheType.CACHE_NULL)) {
            if (cache == null) {
                ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
                Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
                // 通过反射创建model的实例
                cache = clazz.getDeclaredConstructor().newInstance();
            }
            redisService.set(redisKey, cache, redisTimeOut);
            return cache;
        } else {
            if (cache == null) {
                return null;
            }
            redisService.set(redisKey, cache, redisTimeOut);
            return cache;
        }
    }

    /**
     * 获取缓存对象方法
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    public T findCache(Object... key) {
        String redisKey = String.format(redisPrefix, key);
        if (cacheType.equals(CacheType.CACHE_USING_BLOOM_FILTER)) {
            if (rBloomFilter.contains(redisKey)) {
                return null;
            }
        }
        Object obj = redisService.get(redisKey);
        if (obj == null) {
            synchronized (this) {
                // 再查一次，防止上个已经抢到锁的线程已经更新过了
                obj = redisService.get(redisKey);
                if (obj != null) {
                    return (T) obj;
                }
                return cacheAndGetByKey(key);
            }
        }
        return (T) obj;
    }

    /**
     * 删除缓存对象方法
     *
     * @param key 缓存key
     */
    public void deleteCache(Object... key) {
        Optional.ofNullable(key).filter(ArrayUtil::isNotEmpty).ifPresent(o -> {
            String redisKey = String.format(redisPrefix, key);
            redisService.del(redisKey);
        });
    }

    /**
     * 删除缓存对象方法
     *
     * @param keys 缓存key
     */
    public void deleteCache(List<List<Object>> keys) {
        Optional.ofNullable(keys).ifPresent(o -> {
            Set<String> redisKeys = new HashSet<>();
            for (List<Object> key : o) {
                Object[] strings = key.toArray();
                String redisKey = String.format(redisPrefix, strings);
                redisKeys.add(redisKey);
            }
            redisService.del(redisKeys);
        });
    }

}
