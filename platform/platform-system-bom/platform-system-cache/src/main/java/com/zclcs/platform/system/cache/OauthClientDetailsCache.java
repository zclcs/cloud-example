package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.bean.cache.OauthClientDetailsCacheVo;
import com.zclcs.platform.system.api.fegin.RemoteClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class OauthClientDetailsCache extends CacheService<OauthClientDetailsCacheVo> {

    private RemoteClientDetailsService remoteClientDetailsService;

    public OauthClientDetailsCache() {
        super(RedisCachePrefix.CLIENT_DETAILS_PREFIX);
    }

    @Autowired
    public void setRemoteClientDetailsService(RemoteClientDetailsService remoteClientDetailsService) {
        this.remoteClientDetailsService = remoteClientDetailsService;
    }

    @Override
    protected OauthClientDetailsCacheVo findByKey(Object... key) {
        return remoteClientDetailsService.findByClientId((String) key[0]);
    }

    @Override
    protected OauthClientDetailsCacheVo serialization(String json) throws JsonProcessingException {
        return super.getObjectMapper().readValue(json, OauthClientDetailsCacheVo.class);
    }
}
