package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.bean.cache.UserCacheBean;
import com.zclcs.platform.system.api.fegin.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class UserCache extends CacheService<UserCacheBean> {

    private RemoteUserService remoteUserService;

    public UserCache() {
        super(RedisCachePrefix.USER);
    }

    @Autowired
    public void setRemoteUserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }


    @Override
    protected UserCacheBean findByKey(Object... key) {
        return remoteUserService.findByUsername((String) key[0]);
    }
}
