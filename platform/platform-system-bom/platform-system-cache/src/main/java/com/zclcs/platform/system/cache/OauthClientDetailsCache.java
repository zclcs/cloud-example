package com.zclcs.platform.system.cache;

import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.fegin.RemoteClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouc
 */
@Service
public class OauthClientDetailsCache extends CacheService<OauthClientDetails> {

    private RemoteClientDetailsService remoteClientDetailsService;

    public OauthClientDetailsCache(RedisService redisService) {
        super(redisService, RedisCachePrefixConstant.CLIENT_DETAILS, CacheType.CACHE_NULL, null);
    }

    @Autowired
    public void setRemoteClientDetailsService(RemoteClientDetailsService remoteClientDetailsService) {
        this.remoteClientDetailsService = remoteClientDetailsService;
    }

    @Override
    protected OauthClientDetails findByKey(Object... key) {
        return remoteClientDetailsService.findByClientId((String) key[0]);
    }
}
