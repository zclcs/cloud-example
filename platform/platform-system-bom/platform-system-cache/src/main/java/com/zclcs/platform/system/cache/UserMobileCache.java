package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.fegin.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class UserMobileCache extends CacheService<String> {

    private RemoteUserService remoteUserService;

    public UserMobileCache(RedisService redisService) {
        super(redisService, RedisCachePrefix.USER_MOBILE, CacheType.CACHE_NULL, null);
    }

    @Autowired
    public void setRemoteUserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }


    @Override
    protected String findByKey(Object... key) {
        return remoteUserService.findByMobile((String) key[0]);
    }
}
