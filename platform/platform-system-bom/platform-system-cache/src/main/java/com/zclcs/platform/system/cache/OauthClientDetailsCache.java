package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.bean.cache.OauthClientDetailsCacheBean;
import com.zclcs.platform.system.api.fegin.RemoteClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class OauthClientDetailsCache extends CacheService<OauthClientDetailsCacheBean> {

    private RemoteClientDetailsService remoteClientDetailsService;

    public OauthClientDetailsCache() {
        super(RedisCachePrefix.CLIENT_DETAILS);
    }

    @Autowired
    public void setRemoteClientDetailsService(RemoteClientDetailsService remoteClientDetailsService) {
        this.remoteClientDetailsService = remoteClientDetailsService;
    }

    @Override
    protected OauthClientDetailsCacheBean findByKey(Object... key) {
        return remoteClientDetailsService.findByClientId((String) key[0]);
    }
}
