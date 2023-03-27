package com.zclcs.common.redis.starter.configure;

import cn.hutool.core.util.StrUtil;
import com.zclcs.common.redis.starter.properties.MyLettuceRedisProperties;
import org.redisson.api.NameMapper;

/**
 * @author zclcs
 */
public class MyNameMapper implements NameMapper {

    /**
     * 字典表缓存前缀
     */
    private final MyLettuceRedisProperties properties;

    public MyNameMapper(MyLettuceRedisProperties properties) {
        this.properties = properties;
    }

    @Override
    public String map(String name) {
        return (name == null ? null : StrUtil.addPrefixIfNot(name, properties.getRedisCachePrefix()));
    }

    @Override
    public String unmap(String name) {
        return (name == null ? null : StrUtil.addPrefixIfNot(name, properties.getRedisCachePrefix()));
    }
}
