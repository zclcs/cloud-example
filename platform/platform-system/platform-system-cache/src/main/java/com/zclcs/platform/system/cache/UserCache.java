package com.zclcs.platform.system.cache;

import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.User;
import com.zclcs.platform.system.api.fegin.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouc
 */
@Service
public class UserCache extends CacheService<User> {

    private RemoteUserService remoteUserService;

    public UserCache(RedisService redisService) {
        super(redisService, RedisCachePrefixConstant.USER, CacheType.CACHE_NULL, null);
    }

    @Autowired
    public void setRemoteUserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }


    @Override
    protected User findByKey(Object... key) {
        return remoteUserService.findByUsername((String) key[0]).getData();
    }
}
