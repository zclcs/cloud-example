package com.houkunlin.system.dict.starter.store;

import com.houkunlin.system.dict.starter.bean.DictTypeVo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis 字典存储配置
 *
 * @author zclcs
 */
@ConditionalOnClass(RedisTemplate.class)
@Configuration(proxyBeanMethods = false)
public class RedisDictStoreConfiguration {

    /**
     * 当环境中不存在 DictStore Bean 的时候创建一个默认的 RedisDictStore Bean 实例
     *
     * @return {@link DictStore}
     */
    @ConditionalOnProperty(prefix = "system.dict", name = "store-type", havingValue = "AUTO", matchIfMissing = true)
    @Bean
    @ConditionalOnMissingBean
    public DictStore dictStoreAuto(final RedisTemplate<String, DictTypeVo> redisTemplate1, final StringRedisTemplate redisTemplate2, final RemoteDict remoteDict) {
        return new RedisDictStore(redisTemplate1, redisTemplate2, remoteDict);
    }

    /**
     * 当环境中不存在 DictStore Bean 的时候创建一个默认的 RedisDictStore Bean 实例
     *
     * @return {@link DictStore}
     */
    @ConditionalOnProperty(prefix = "system.dict", name = "store-type", havingValue = "REDIS")
    @Bean
    @ConditionalOnMissingBean
    public DictStore dictStoreRedis(final RedisTemplate<String, DictTypeVo> redisTemplate1, final StringRedisTemplate redisTemplate2, final RemoteDict remoteDict) {
        return new RedisDictStore(redisTemplate1, redisTemplate2, remoteDict);
    }
}
