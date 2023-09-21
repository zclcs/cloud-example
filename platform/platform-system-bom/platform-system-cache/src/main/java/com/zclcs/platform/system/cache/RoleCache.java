package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.bean.cache.RoleCacheVo;
import com.zclcs.platform.system.api.fegin.RemoteRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class RoleCache extends CacheService<RoleCacheVo> {

    private RemoteRoleService remoteRoleService;

    public RoleCache() {
        super(RedisCachePrefix.ROLE_PREFIX, false);
    }

    @Autowired
    public void setRemoteRoleService(RemoteRoleService remoteRoleService) {
        this.remoteRoleService = remoteRoleService;
    }

    @Override
    protected RoleCacheVo findByKey(Object... key) {
        return remoteRoleService.findByRoleId((Long) key[0]);
    }

    @Override
    protected RoleCacheVo serialization(String json) throws JsonProcessingException {
        return super.getObjectMapper().readValue(json, RoleCacheVo.class);
    }
}
