package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.fegin.RemoteMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class UserPermissionsCache extends CacheService<List<String>> {

    private RemoteMenuService remoteMenuService;

    public UserPermissionsCache() {
        super(RedisCachePrefix.USER_PERMISSIONS_PREFIX);
    }

    @Autowired
    public void setRemoteMenuService(RemoteMenuService remoteMenuService) {
        this.remoteMenuService = remoteMenuService;
    }


    @Override
    protected List<String> findByKey(Object... key) {
        return remoteMenuService.findUserPermissions((String) key[0]);
    }

    @Override
    protected List<String> serialization(String json) throws JsonProcessingException {
        TypeReference<List<String>> valueTypeRef = new TypeReference<>() {
        };
        return super.getObjectMapper().readValue(json, valueTypeRef);
    }
}
