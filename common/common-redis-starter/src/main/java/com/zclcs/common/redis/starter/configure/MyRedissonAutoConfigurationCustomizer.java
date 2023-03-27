package com.zclcs.common.redis.starter.configure;

import com.zclcs.common.redis.starter.properties.MyLettuceRedisProperties;
import lombok.RequiredArgsConstructor;
import org.redisson.api.NameMapper;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;

/**
 * @author zclcs
 */
@RequiredArgsConstructor
public class MyRedissonAutoConfigurationCustomizer implements RedissonAutoConfigurationCustomizer {

    private final MyLettuceRedisProperties properties;

    @Override
    public void customize(Config cfg) {
        NameMapper nameMapper = new MyNameMapper(properties);
        if (cfg.isClusterConfig()) {
            cfg.useClusterServers().setNameMapper(nameMapper);
        } else if (cfg.isSentinelConfig()) {
            cfg.useSentinelServers().setNameMapper(nameMapper);
        } else {
            cfg.useSingleServer().setNameMapper(nameMapper);
        }
    }
}
