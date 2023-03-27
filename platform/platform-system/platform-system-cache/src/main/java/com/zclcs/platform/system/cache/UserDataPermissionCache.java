package com.zclcs.platform.system.cache;

import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.fegin.RemoteUserDataPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouc
 */
@Service
public class UserDataPermissionCache extends CacheService<List<Long>> {

    private RemoteUserDataPermissionService remoteUserDataPermissionService;

    public UserDataPermissionCache(RedisService redisService) {
        super(redisService, RedisCachePrefixConstant.USER_DATA_PERMISSION, CacheType.CACHE_USING_BLOOM_FILTER,
                redisService.getBloomFilter(RedisCachePrefixConstant.BLOOM_FILTER_USER_DATA_PERMISSION));
        super.init(10000, 0.03);
    }

    @Autowired
    public void setRemoteUserDataPermissionService(RemoteUserDataPermissionService remoteUserDataPermissionService) {
        this.remoteUserDataPermissionService = remoteUserDataPermissionService;
    }


    @Override
    protected List<Long> findByKey(Object... key) {
        return remoteUserDataPermissionService.findByUserId((Long) key[0]).getData();
    }
}
