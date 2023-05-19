package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.Role;
import com.zclcs.platform.system.api.fegin.RemoteRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class RoleCache extends CacheService<Role> {

    private RemoteRoleService remoteRoleService;

    public RoleCache(RedisService redisService) {
        super(RedisCachePrefix.ROLE, redisService.getBloomFilter(RedisCachePrefix.BLOOM_FILTER_ROLE), 10000, 0.03);
    }

    @Autowired
    public void setRemoteRoleService(RemoteRoleService remoteRoleService) {
        this.remoteRoleService = remoteRoleService;
    }

    @Override
    protected Role findByKey(Object... key) {
        return remoteRoleService.findByRoleId((Long) key[0]);
    }
}
