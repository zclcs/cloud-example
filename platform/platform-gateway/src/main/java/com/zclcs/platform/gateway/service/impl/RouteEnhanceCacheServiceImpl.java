package com.zclcs.platform.gateway.service.impl;

import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.service.RouteEnhanceCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author zclcs
 */
@Slf4j
@Service
public class RouteEnhanceCacheServiceImpl implements RouteEnhanceCacheService {

    private RedisService redisService;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public Set<Object> getBlackList(String ip) {
        String key = RouteEnhanceCacheUtil.getBlackListCacheKey(ip);
        return redisService.sGet(key);
    }

    @Override
    public Set<Object> getBlackList() {
        String key = RouteEnhanceCacheUtil.getBlackListCacheKey();
        return redisService.sGet(key);
    }

    @Override
    public Object getRateLimitRule(String uri, String method) {
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(uri, method);
        return redisService.get(key);
    }

    @Override
    public int getCurrentRequestCount(String uri, String ip) {
        String key = RouteEnhanceCacheUtil.getRateLimitCountKey(uri, ip);
        return redisService.hasKey(key) ? (int) redisService.get(key) : 0;
    }

    @Override
    public void setCurrentRequestCount(String uri, String ip, Long time) {
        String key = RouteEnhanceCacheUtil.getRateLimitCountKey(uri, ip);
        redisService.set(key, 1, time);
    }

    @Override
    public void incrCurrentRequestCount(String uri, String ip) {
        String key = RouteEnhanceCacheUtil.getRateLimitCountKey(uri, ip);
        redisService.incr(key, 1L);
    }

}
