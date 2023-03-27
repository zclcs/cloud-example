package com.zclcs.platform.system.cache;

import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.fegin.RemoteUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouc
 */
@Service
public class UserRoleCache extends CacheService<List<Long>> {

    private RemoteUserRoleService remoteUserRoleService;

    public UserRoleCache(RedisService redisService) {
        super(redisService, RedisCachePrefixConstant.USER_ROLE, CacheType.CACHE_USING_BLOOM_FILTER,
                redisService.getBloomFilter(RedisCachePrefixConstant.BLOOM_FILTER_USER_ROLE));
        super.init(10000, 0.03);
    }

    @Autowired
    public void setRemoteUserRoleService(RemoteUserRoleService remoteUserRoleService) {
        this.remoteUserRoleService = remoteUserRoleService;
    }


    @Override
    protected List<Long> findByKey(Object... key) {
        return remoteUserRoleService.findByUserId((Long) key[0]).getData();
    }
}
