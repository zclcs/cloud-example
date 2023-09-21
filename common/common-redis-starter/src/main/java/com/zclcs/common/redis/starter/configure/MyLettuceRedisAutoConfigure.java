package com.zclcs.common.redis.starter.configure;

import com.zclcs.common.redis.starter.properties.MyLettuceRedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Lettuce Redis配置
 *
 * @author zclcs
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(MyLettuceRedisProperties.class)
@ConditionalOnProperty(value = "my.lettuce.redis.enable", havingValue = "true", matchIfMissing = true)
public class MyLettuceRedisAutoConfigure {

    private final MyLettuceRedisProperties myLettuceRedisProperties;

    @Bean(name = "redisTemplate")
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        MyStringRedisSerializer myStringRedisSerializer = new MyStringRedisSerializer(myLettuceRedisProperties.getRedisCachePrefix());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setKeySerializer(myStringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

//    @Bean
//    public MyRedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer() {
//        return new MyRedissonAutoConfigurationCustomizer(myLettuceRedisProperties);
//    }
}
