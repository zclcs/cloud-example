package com.zclcs.platform.gateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.service.RouteEnhanceCacheService;
import com.zclcs.platform.system.api.bean.cache.BlackListCacheVo;
import com.zclcs.platform.system.api.bean.cache.RateLimitRuleCacheVo;
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
    public Set<BlackListCacheVo> getBlackList(String ip) throws JsonProcessingException {
        String key = RouteEnhanceCacheUtil.getBlackListCacheKey(ip);
        Set<Object> objects = redisService.sGet(key);
        Set<BlackListCacheVo> blackListCacheVos = new HashSet<>();
        for (Object object : objects) {
            BlackListCacheVo blackListCacheVo = objectMapper.readValue((String) object, BlackListCacheVo.class);
            blackListCacheVos.add(blackListCacheVo);
        }
        return blackListCacheVos;
    }

    @Override
    public Set<BlackListCacheVo> getBlackList() throws JsonProcessingException {
        String key = RouteEnhanceCacheUtil.getBlackListCacheKey();
        Set<Object> objects = redisService.sGet(key);
        Set<BlackListCacheVo> blackListCacheVos = new HashSet<>();
        for (Object object : objects) {
            BlackListCacheVo blackListCacheVo = objectMapper.readValue((String) object, BlackListCacheVo.class);
            blackListCacheVos.add(blackListCacheVo);
        }
        return blackListCacheVos;
    }

    @Override
    public RateLimitRuleCacheVo getRateLimitRule(String uri, String method) throws JsonProcessingException {
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(uri, method);
        Object o = redisService.get(key);
        if (o == null) {
            return null;
        }
        return objectMapper.readValue((String) o, RateLimitRuleCacheVo.class);
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
