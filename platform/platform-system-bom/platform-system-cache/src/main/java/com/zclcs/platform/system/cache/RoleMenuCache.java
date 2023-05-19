package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.fegin.RemoteRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class RoleMenuCache extends CacheService<List<Long>> {

    private RemoteRoleMenuService remoteRoleMenuService;

    public RoleMenuCache() {
        super(RedisCachePrefix.ROLE_MENU);
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
