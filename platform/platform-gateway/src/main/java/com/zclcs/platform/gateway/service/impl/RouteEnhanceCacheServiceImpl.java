package com.zclcs.platform.gateway.service.impl;

import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.service.RouteEnhanceCacheService;
import com.zclcs.platform.system.api.bean.cache.BlackListCacheVo;
import com.zclcs.platform.system.api.bean.cache.RateLimitRuleCacheVo;
import jakarta.validation.constraints.NotNull;
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

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public Set<BlackListCacheVo> getBlackList(String ip) {
        String key = RouteEnhanceCacheUtil.getBlackListCacheKey(ip);
        return getBlackListCacheVos(key);
    }

    @Override
    public Set<BlackListCacheVo> getBlackList() {
        String key = RouteEnhanceCacheUtil.getBlackListCacheKey();
        return getBlackListCacheVos(key);
    }

    @NotNull
    private Set<BlackListCacheVo> getBlackListCacheVos(String key) {
        Set<String> members = redisService.sMembers(key);
        Set<BlackListCacheVo> blackListCacheVos = new HashSet<>();
        if (members != null) {
            for (String member : members) {
                BlackListCacheVo blackListCacheVo = JsonUtil.readValue(member, BlackListCacheVo.class);
                blackListCacheVos.add(blackListCacheVo);
            }
        }
        return blackListCacheVos;
    }

    @Override
    public RateLimitRuleCacheVo getRateLimitRule(String uri, String method) {
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(uri, method);
        Object o = redisService.get(key);
        if (o == null) {
            return null;
        }
        return JsonUtil.readValue((String) o, RateLimitRuleCacheVo.class);
    }

}
