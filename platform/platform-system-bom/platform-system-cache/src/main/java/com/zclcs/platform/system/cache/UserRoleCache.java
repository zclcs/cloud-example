package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.fegin.RemoteUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class UserRoleCache extends CacheService<List<Long>> {

    private RemoteUserRoleService remoteUserRoleService;

    public UserRoleCache() {
        super(RedisCachePrefix.USER_ROLE, false);
    }

    @Autowired
    public void setRemoteUserRoleService(RemoteUserRoleService remoteUserRoleService) {
        this.remoteUserRoleService = remoteUserRoleService;
    }


    @Override
    protected List<Long> findByKey(Object... key) {
        return remoteUserRoleService.findByUserId((Long) key[0]);
    }

    @Override
    protected List<Long> serialization(String json) throws JsonProcessingException {
        TypeReference<List<Long>> valueTypeRef = new TypeReference<>() {
        };
        return super.getObjectMapper().readValue(json, valueTypeRef);
    }
}
