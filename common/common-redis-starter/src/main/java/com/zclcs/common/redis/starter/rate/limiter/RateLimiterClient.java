package com.zclcs.common.redis.starter.rate.limiter;

import com.zclcs.common.redis.starter.exception.RateLimiterException;
import com.zclcs.common.redis.starter.function.CheckedSupplier;

import java.util.concurrent.TimeUnit;

/**
 * RedisRateLimiter 限流 Client
 *
 * @author zclcs
 */
public interface RateLimiterClient {

    /**
     * 服务是否被限流
     *
     * @param key 自定义的key，请保证唯一
     * @param max 支持的最大请求
     * @param ttl 时间,单位默认为秒（seconds）
     * @return 是否允许
     */
    default boolean isAllowed(String key, long max, long ttl) {
        return this.isAllowed(key, max, ttl, TimeUnit.SECONDS);
    }

    /**
     * 服务是否被限流
     *
     * @param key      自定义的key，请保证唯一
     * @param max      支持的最大请求
     * @param ttl      时间
     * @param timeUnit 时间单位
     * @return 是否允许
     */
    boolean isAllowed(String key, long max, long ttl, TimeUnit timeUnit);

    /**
     * 服务限流，被限制时抛出 RateLimiterException 异常，需要自行处理异常
     *
     * @param key      自定义的key，请保证唯一
     * @param max      支持的最大请求
     * @param ttl      时间
     * @param supplier Supplier 函数式
     * @param <T>      泛型
     * @return 函数执行结果
     */
    default <T> T allow(String key, long max, long ttl, CheckedSupplier<T> supplier) {
        return allow(key, max, ttl, TimeUnit.SECONDS, supplier);
    }

    /**
     * 服务限流，被限制时抛出 RateLimiterException 异常，需要自行处理异常
     *
     * @param key      自定义的key，请保证唯一
     * @param max      支持的最大请求
     * @param ttl      时间
     * @param timeUnit 时间单位
     * @param supplier Supplier 函数式
     * @param <T>      泛型
     * @return 函数执行结果
     */
    default <T> T allow(String key, long max, long ttl, TimeUnit timeUnit, CheckedSupplier<T> supplier) {
        boolean isAllowed = this.isAllowed(key, max, ttl, timeUnit);
        if (isAllowed) {
            try {
                return supplier.get();
            } catch (Throwable e) {
                throw runtime(e);
            }
        }
        throw new RateLimiterException(key, max, ttl, timeUnit);
    }

    /**
     * 不采用 RuntimeException 包装，直接抛出，使异常更加精准
     *
     * @param throwable Throwable
     * @param <T>       泛型标记
     * @return Throwable
     * @throws T 泛型
     */
    default <T extends Throwable> T runtime(Throwable throwable) throws T {
        throw (T) throwable;
    }

}
