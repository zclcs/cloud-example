package com.zclcs.cloud.lib.core.utils;

import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import lombok.experimental.UtilityClass;

/**
 * @author zclcs
 */
@UtilityClass
public class RouteEnhanceCacheUtil {


    public String getBlackListCacheKey(String ip) {
        if (CommonCore.LOCALHOST.equalsIgnoreCase(ip)) {
            ip = CommonCore.LOCALHOST_IP;
        }
        return String.format("%s%s", RedisCachePrefix.BLACKLIST_CACHE_KEY_PREFIX, ip);
    }

    public String getBlackListCacheKey() {
        return String.format("%sally", RedisCachePrefix.BLACKLIST_CACHE_KEY_PREFIX);
    }

    public String getRateLimitCacheKey(String uri, String method) {
        return String.format("%s%s:%s", RedisCachePrefix.RATE_LIMIT_CACHE_KEY_PREFIX, uri, method);
    }

    public String getRateLimitCountKey(String uri, String ip) {
        return String.format("%s%s:%s", RedisCachePrefix.RATE_LIMIT_COUNT_KEY_PREFIX, uri, ip);
    }
}
