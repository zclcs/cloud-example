//package com.zclcs.common.redis.starter.configure;
//
//import com.zclcs.common.redis.starter.properties.MyLettuceRedisProperties;
//import org.redisson.api.NameMapper;
//import org.redisson.config.Config;
//import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
//
///**
// * @author zclcs
// */
//public class MyRedissonAutoConfigurationCustomizer implements RedissonAutoConfigurationCustomizer {
//
//    /**
//     * 字典表缓存前缀
//     */
//    private final MyLettuceRedisProperties myLettuceRedisProperties;
//
//    public MyRedissonAutoConfigurationCustomizer(MyLettuceRedisProperties myLettuceRedisProperties) {
//        this.myLettuceRedisProperties = myLettuceRedisProperties;
//    }
//
//    @Override
//    public void customize(Config cfg) {
//        NameMapper nameMapper = new MyNameMapper(myLettuceRedisProperties.getRedisCachePrefix());
//        if (cfg.isClusterConfig()) {
//            cfg.useClusterServers().setNameMapper(nameMapper);
//        } else if (cfg.isSentinelConfig()) {
//            cfg.useSentinelServers().setNameMapper(nameMapper);
//        } else {
//            cfg.useSingleServer().setNameMapper(nameMapper);
//        }
//    }
//}
