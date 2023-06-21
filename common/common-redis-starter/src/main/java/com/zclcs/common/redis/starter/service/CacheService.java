package com.zclcs.common.redis.starter.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zclcs.common.redis.starter.enums.CacheType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zclcs
 * 分布式、高并发业务场景缓存处理抽象类
 */
@Slf4j
public abstract class CacheService<T> {

    /**
     * 存储前缀
     */
    final String redisPrefix;
    /**
     * 未hit到数据库数据时的存储类型
     */
    CacheType cacheType = CacheType.CACHE_NULL;
    /**
     * redis服务类
     */
    RedisService redisService;
    /**
     * 布隆过滤器
     */
    RBloomFilter<String> rBloomFilter;
    /**
     * 缓存过期时间
     */
    long timeOut = 2L * 24L * 60L * 60L;
    /**
     * 是否启用缓存-内存
     */
    boolean withCache = true;
    /**
     * 缓存最大容量
     */
    int maximumSize = 500;
    /**
     * 缓存初始化容量
     */
    int initialCapacity = 50;
    /**
     * 有效期时长
     */
    Duration duration = Duration.ofSeconds(30);
    /**
     * 在有效期内同一个字典值未命中指定次数  将快速返回，不再重复请求获取数据字典信息。
     * 例如：一个 userType 类型 值为 2 的字典，在 30 秒内超过 50 次找不到字典文本，那么在本次 30 秒的周期内将不再继续请求字典信息，而是直接返回一个 null 值。
     * 特别是在使用 Redis 存储数据字典信息时，频繁未命中数据将会频繁进行网络IO，因此可能会增加单个接口返回数据的耗时（数据量大转换次数多时）
     */
    int missNum = 50;
    /**
     * 字典值缓存
     */
    Cache<String, T> cache;
    Cache<String, AtomicInteger> missCache;

    /**
     * 构造方法
     * 缓存类型: 缓存null对象
     * 过期时间: 2天
     *
     * @param redisPrefix 缓存前缀
     */
    public CacheService(String redisPrefix) {
        this.redisPrefix = redisPrefix;
        cache = buildCache(maximumSize, initialCapacity, duration);
        missCache = buildCache(maximumSize, initialCapacity, duration);
    }

    /**
     * 构造方法
     * 缓存类型: 缓存null对象
     * 过期时间: 2天
     *
     * @param redisPrefix 缓存前缀
     * @param withCache   是否使用内存缓存
     */
    public CacheService(String redisPrefix, boolean withCache) {
        this.redisPrefix = redisPrefix;
        this.withCache = withCache;
        if (withCache) {
            cache = buildCache(maximumSize, initialCapacity, duration);
            missCache = buildCache(maximumSize, initialCapacity, duration);
        }
    }

    /**
     * 构造方法
     * 缓存类型: 缓存null对象
     * 过期时间: 2天
     *
     * @param redisPrefix 缓存前缀
     * @param timeOut     过期时间
     */
    public CacheService(String redisPrefix, long timeOut) {
        this(redisPrefix);
        this.timeOut = timeOut;
    }

    /**
     * 构造方法
     * 缓存类型: 缓存null对象
     * 过期时间: 2天
     *
     * @param redisPrefix 缓存前缀
     * @param withCache   是否使用内存缓存
     * @param timeOut     过期时间
     */
    public CacheService(String redisPrefix, boolean withCache, long timeOut) {
        this(redisPrefix, withCache);
        this.timeOut = timeOut;
    }

    /**
     * 构造方法
     * 缓存类型: 使用布隆过滤器
     *
     * @param redisPrefix        缓存前缀
     * @param rBloomFilter       布隆过滤器
     * @param expectedInsertions 预期数量
     * @param falseProbability   预期错误概率
     */
    public CacheService(String redisPrefix,
                        RBloomFilter<String> rBloomFilter,
                        long expectedInsertions,
                        double falseProbability) {
        this(redisPrefix);
        this.cacheType = CacheType.CACHE_USING_BLOOM_FILTER;
        this.rBloomFilter = rBloomFilter;
        this.rBloomFilter.tryInit(expectedInsertions, falseProbability);
    }

    /**
     * 构造方法
     * 缓存类型: 使用布隆过滤器
     *
     * @param redisPrefix        缓存前缀
     * @param withCache          是否使用内存缓存
     * @param rBloomFilter       布隆过滤器
     * @param expectedInsertions 预期数量
     * @param falseProbability   预期错误概率
     */
    public CacheService(String redisPrefix,
                        boolean withCache,
                        RBloomFilter<String> rBloomFilter,
                        long expectedInsertions,
                        double falseProbability) {
        this(redisPrefix, withCache);
        this.cacheType = CacheType.CACHE_USING_BLOOM_FILTER;
        this.rBloomFilter = rBloomFilter;
        this.rBloomFilter.tryInit(expectedInsertions, falseProbability);
    }

    /**
     * 构造方法
     * 缓存类型: 使用布隆过滤器
     *
     * @param redisPrefix        缓存前缀
     * @param rBloomFilter       布隆过滤器
     * @param expectedInsertions 预期数量
     * @param falseProbability   预期错误概率
     * @param timeOut            过期时间
     */
    public CacheService(String redisPrefix,
                        RBloomFilter<String> rBloomFilter,
                        long expectedInsertions,
                        double falseProbability,
                        long timeOut) {
        this(redisPrefix, rBloomFilter, expectedInsertions, falseProbability);
        this.timeOut = timeOut;
    }


    /**
     * 构造方法
     * 缓存类型: 使用布隆过滤器
     *
     * @param redisPrefix        缓存前缀
     * @param withCache          是否使用内存缓存
     * @param rBloomFilter       布隆过滤器
     * @param expectedInsertions 预期数量
     * @param falseProbability   预期错误概率
     * @param timeOut            过期时间
     */
    public CacheService(String redisPrefix,
                        boolean withCache,
                        RBloomFilter<String> rBloomFilter,
                        long expectedInsertions,
                        double falseProbability,
                        long timeOut) {
        this(redisPrefix, withCache, rBloomFilter, expectedInsertions, falseProbability);
        this.timeOut = timeOut;
    }

    private <K1, V1> Cache<K1, V1> buildCache(int maximumSize, int initialCapacity, Duration duration) {
        final Caffeine<Object, Object> builder = Caffeine.newBuilder();
        builder
                .maximumSize(maximumSize)
                .initialCapacity(initialCapacity)
                .expireAfterWrite(duration);
        return builder.build();
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * fegin 远程调用返回缓存对象方法
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    protected abstract T findByKey(Object... key);

    /**
     * 缓存并获取缓存对象方法
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    @SneakyThrows
    public T cacheAndGetByKey(Object... key) {
        // 1 布隆过滤器
        // 1.1 存在一定量的误判 可以忽略不计（原理是hash冲突）
        // 1.2 布隆过滤器不支持删除（现在库中不存在 test2 用户，查询库中不存在后就加入布隆过滤器中 那后续 test2 又入库了 这里就一直返回不存在）
        // 1.3 解决方案
        // 1.3.1 只能使用表主键缓存 特性：新增之后不能改 数据新增后的key都是不一样的
        // 1.3.2 使用业务key缓存暂时没有解决办法（不管怎么定，业务key始终会有同样的key 会有误判的情况）
        // 2 直接缓存 null 对象
        // 2.1 存在攻击性的问题 一但遭受攻击 redis中会多很多不存在的key
        // 3 综合以上情况的解决方案
        // 3.1 缓存必须设置ttl时间
        // 3.2 最好使用表id做redis key 存储 (关联关系不能用表id) 一对多或者多对多
        // 3.3 如果必须用业务key存储建议缓存时直接缓存null 对象
        // 3.4 增删改都必须要删除对应的key
        T cache;
        try {
            cache = this.findByKey(key);
        } catch (Exception e) {
            return null;
        }
        return cacheKey(cache, key);
    }

    /**
     * 缓存并获取缓存对象方法
     *
     * @param cache 对象
     */
    @SneakyThrows
    public T cacheKey(T cache, Object... key) {
        String redisKey = String.format(redisPrefix, key);
        Long redisTimeOut = timeOut + RandomUtil.randomLong(1, 360);
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
        } else {
            if (cache == null) {
                ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
                Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
                // 通过反射创建model的实例
                cache = clazz.getDeclaredConstructor().newInstance();
            }
            redisService.set(redisKey, cache, redisTimeOut);
        }
        return cache;
    }

    /**
     * 获取缓存对象方法
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    public boolean checkCache(Object... key) {
        String redisKey = String.format(redisPrefix, key);
        if (cacheType.equals(CacheType.CACHE_USING_BLOOM_FILTER)) {
            if (rBloomFilter.contains(redisKey)) {
                return true;
            }
        }
        return redisService.hasKey(redisKey);
    }

    /**
     * 获取缓存对象方法
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    public T findCache(Object... key) {
        if (withCache) {
            String redisKey = String.format(redisPrefix, key);
            T result = cache.getIfPresent(redisKey);
            if (result == null) {
                synchronized (this) {
                    result = cache.getIfPresent(redisKey);
                    if (result != null) {
                        return result;
                    }
                    T cacheWithRedis = findCacheWithRedis(key);
                    cache.put(redisKey, cacheWithRedis);
                    return cacheWithRedis;
                }
            }
            return result;
        } else {
            return findCacheWithRedis(key);
        }
    }

    private T findCacheWithRedis(Object... key) {
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
