//package com.zclcs.common.redis.starter.configure;
//
//import cn.hutool.core.util.StrUtil;
//import org.redisson.api.NameMapper;
//
///**
// * @author zclcs
// */
//public class MyNameMapper implements NameMapper {
//
//    /**
//     * 字典表缓存前缀
//     */
//    private final String redisCachePrefix;
//
//    public MyNameMapper(String redisCachePrefix) {
//        this.redisCachePrefix = redisCachePrefix;
//    }
//
//    @Override
//    public String map(String name) {
//        return (name == null ? null : StrUtil.addPrefixIfNot(name, redisCachePrefix));
//    }
//
//    @Override
//    public String unmap(String name) {
//        return (name == null ? null : StrUtil.addPrefixIfNot(name, redisCachePrefix));
//    }
//}
