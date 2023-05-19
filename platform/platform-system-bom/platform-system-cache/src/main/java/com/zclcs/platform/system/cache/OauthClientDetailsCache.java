package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.entity.OauthClientDetails;
import com.zclcs.platform.system.api.fegin.RemoteClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class OauthClientDetailsCache extends CacheService<OauthClientDetails> {

    private RemoteClientDetailsService remoteClientDetailsService;

    public OauthClientDetailsCache() {
        super(RedisCachePrefix.CLIENT_DETAILS);
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
