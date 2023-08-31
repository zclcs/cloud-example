package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.bean.cache.UserCacheVo;
import com.zclcs.platform.system.api.fegin.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class UserCache extends CacheService<UserCacheVo> {

    private RemoteUserService remoteUserService;

    public UserCache() {
        super(RedisCachePrefix.USER_PREFIX);
    }

    @Autowired
    public void setRemoteUserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    @Override
    protected UserCacheVo findByKey(Object... key) {
        return remoteUserService.findByUsername((String) key[0]);
    }

    @Override
    protected UserCacheVo serialization(String json) throws JsonProcessingException {
        return super.getObjectMapper().readValue(json, UserCacheVo.class);
    }
}
