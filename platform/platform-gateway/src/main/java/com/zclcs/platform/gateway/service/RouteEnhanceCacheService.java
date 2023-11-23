package com.zclcs.platform.gateway.service;

import com.zclcs.platform.system.api.bean.cache.BlackListCacheVo;
import com.zclcs.platform.system.api.bean.cache.RateLimitRuleCacheVo;

import java.util.Set;

/**
 * @author zclcs
 */
public interface RouteEnhanceCacheService {

    /**
     * 从缓存中获取黑名单规则
     *
     * @param ip ip
     * @return 黑名单
     */
    Set<BlackListCacheVo> getBlackList(String ip);

    /**
     * 从缓存中获取黑名单规则
     *
     * @return 黑名单
     */
    Set<BlackListCacheVo> getBlackList();

    /**
     * 从缓存中获取限流规则
     *
     * @param uri    uri
     * @param method method
     * @return 限流规则
     */
    RateLimitRuleCacheVo getRateLimitRule(String uri, String method);

}
