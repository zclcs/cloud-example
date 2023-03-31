package com.zclcs.common.core.utils;


import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import lombok.experimental.UtilityClass;

/**
 * @author zclcs
 */
@UtilityClass
public class RouteEnhanceCacheUtil {

    public String getBlackListCacheKey(String ip) {
        if (MyConstant.LOCALHOST.equalsIgnoreCase(ip)) {
            ip = MyConstant.LOCALHOST_IP;
        }
        return String.format("%s%s", RedisCachePrefixConstant.BLACKLIST_CACHE_KEY_PREFIX, ip);
    }

    public String getBlackListCacheKey() {
        return String.format("%sally", RedisCachePrefixConstant.BLACKLIST_CACHE_KEY_PREFIX);
    }

    public String getRateLimitCacheKey(String uri, String method) {
        return String.format("%s%s:%s", RedisCachePrefixConstant.RATE_LIMIT_CACHE_KEY_PREFIX, uri, method);
    }

    public String getRateLimitCountKey(String uri, String ip) {
        return String.format("%s%s:%s", RedisCachePrefixConstant.RATE_LIMIT_COUNT_KEY_PREFIX, uri, ip);
    }
}
