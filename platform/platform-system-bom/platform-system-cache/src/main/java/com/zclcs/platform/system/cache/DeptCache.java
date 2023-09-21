package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.bean.cache.DeptCacheVo;
import com.zclcs.platform.system.api.fegin.RemoteDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class DeptCache extends CacheService<DeptCacheVo> {

    private RemoteDeptService remoteDeptService;

    public DeptCache() {
        super(RedisCachePrefix.DEPT_PREFIX);
    }

    @Autowired
    public void setRemoteDeptService(RemoteDeptService remoteDeptService) {
        this.remoteDeptService = remoteDeptService;
    }

    @Override
    protected DeptCacheVo findByKey(Object... key) {
        return remoteDeptService.findByDeptId((Long) key[0]);
    }

    @Override
    protected DeptCacheVo serialization(String json) throws JsonProcessingException {
        return super.getObjectMapper().readValue(json, DeptCacheVo.class);
    }

}
