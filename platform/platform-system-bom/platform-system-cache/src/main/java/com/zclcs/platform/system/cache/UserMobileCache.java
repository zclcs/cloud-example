package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.fegin.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class UserMobileCache extends CacheService<String> {

    private RemoteUserService remoteUserService;

    public UserMobileCache() {
        super(RedisCachePrefix.USER_MOBILE);
    }

    @Autowired
    public void setRemoteUserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }


    @Override
    protected String findByKey(Object... key) {
        return remoteUserService.findByMobile((String) key[0]);
    }

    @Override
    protected String serialization(String json) throws JsonProcessingException {
        return json;
    }
}
