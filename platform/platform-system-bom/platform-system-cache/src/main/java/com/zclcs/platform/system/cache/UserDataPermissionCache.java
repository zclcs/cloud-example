package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.fegin.RemoteUserDataPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class UserDataPermissionCache extends CacheService<List<Long>> {

    private RemoteUserDataPermissionService remoteUserDataPermissionService;

    public UserDataPermissionCache() {
        super(RedisCachePrefix.USER_DATA_PERMISSION);
    }

    @Autowired
    public void setRemoteUserDataPermissionService(RemoteUserDataPermissionService remoteUserDataPermissionService) {
        this.remoteUserDataPermissionService = remoteUserDataPermissionService;
    }


    @Override
    protected List<Long> findByKey(Object... key) {
        return remoteUserDataPermissionService.findByUserId((Long) key[0]);
    }
}
