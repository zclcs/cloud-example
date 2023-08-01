package com.zclcs.platform.gateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.service.RouteEnhanceCacheService;
import com.zclcs.platform.system.api.bean.cache.BlackListCacheBean;
import com.zclcs.platform.system.api.bean.cache.RateLimitRuleCacheBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zclcs
 */
@Slf4j
@Service
public class RouteEnhanceCacheServiceImpl implements RouteEnhanceCacheService {

    private RedisService redisService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Set<BlackListCacheBean> getBlackList(String ip) throws JsonProcessingException {
        String key = RouteEnhanceCacheUtil.getBlackListCacheKey(ip);
        Set<Object> objects = redisService.sGet(key);
        Set<BlackListCacheBean> blackListCacheBeans = new HashSet<>();
        for (Object object : objects) {
            BlackListCacheBean blackListCacheBean = objectMapper.readValue((String) object, BlackListCacheBean.class);
            blackListCacheBeans.add(blackListCacheBean);
        }
        return blackListCacheBeans;
    }

    @Override
    public Set<BlackListCacheBean> getBlackList() throws JsonProcessingException {
        String key = RouteEnhanceCacheUtil.getBlackListCacheKey();
        Set<Object> objects = redisService.sGet(key);
        Set<BlackListCacheBean> blackListCacheBeans = new HashSet<>();
        for (Object object : objects) {
            BlackListCacheBean blackListCacheBean = objectMapper.readValue((String) object, BlackListCacheBean.class);
            blackListCacheBeans.add(blackListCacheBean);
        }
        return blackListCacheBeans;
    }

    @Override
    public RateLimitRuleCacheBean getRateLimitRule(String uri, String method) throws JsonProcessingException {
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(uri, method);
        Object o = redisService.get(key);
        if (o == null) {
            return null;
        }
        return objectMapper.readValue((String) o, RateLimitRuleCacheBean.class);
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
