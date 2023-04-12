package com.zclcs.platform.system.cache;

import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.fegin.RemoteRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouc
 */
@Service
public class RoleMenuCache extends CacheService<List<Long>> {

    private RemoteRoleMenuService remoteRoleMenuService;

    public RoleMenuCache(RedisService redisService) {
        super(redisService, RedisCachePrefixConstant.ROLE_MENU, CacheType.CACHE_NULL, null);
    }

    @Autowired
    public void setRemoteRoleMenuService(RemoteRoleMenuService remoteRoleMenuService) {
        this.remoteRoleMenuService = remoteRoleMenuService;
    }

    @Override
    protected List<Long> findByKey(Object... key) {
        return remoteRoleMenuService.findByRoleId((Long) key[0]);
    }
}