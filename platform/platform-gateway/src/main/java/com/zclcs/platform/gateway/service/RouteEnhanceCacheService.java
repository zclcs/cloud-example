package com.zclcs.platform.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    Set<BlackListCacheVo> getBlackList(String ip) throws JsonProcessingException;

    /**
     * 从缓存中获取黑名单规则
     *
     * @return 黑名单
     */
    Set<BlackListCacheVo> getBlackList() throws JsonProcessingException;

    /**
     * 从缓存中获取限流规则
     *
     * @param uri    uri
     * @param method method
     * @return 限流规则
     */
    RateLimitRuleCacheVo getRateLimitRule(String uri, String method) throws JsonProcessingException;

    /**
     * 获取当前请求次数
     *
     * @param uri uri
     * @param ip  ip
     * @return 次数
     */
    int getCurrentRequestCount(String uri, String ip);

    /**
     * 设置请求次数
     *
     * @param uri  uri
     * @param ip   ip
     * @param time time
     */
    void setCurrentRequestCount(String uri, String ip, Long time);

    /**
     * 递增请求次数
     *
     * @param uri uri
     * @param ip  ip
     */
    void incrCurrentRequestCount(String uri, String ip);
}
