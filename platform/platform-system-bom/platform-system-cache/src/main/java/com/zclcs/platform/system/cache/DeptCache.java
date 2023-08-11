package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.bean.cache.DeptCacheBean;
import com.zclcs.platform.system.api.fegin.RemoteDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class DeptCache extends CacheService<DeptCacheBean> {

    private RemoteDeptService remoteDeptService;

    public DeptCache(RedisService redisService) {
        super(RedisCachePrefix.DEPT, false, redisService.getBloomFilter(RedisCachePrefix.BLOOM_FILTER_DEPT), 10000, 0.03);
    }

    @Autowired
    public void setRemoteDeptService(RemoteDeptService remoteDeptService) {
        this.remoteDeptService = remoteDeptService;
    }

    @Override
    protected DeptCacheBean findByKey(Object... key) {
        return remoteDeptService.findByDeptId((Long) key[0]);
    }

    @Override
    protected DeptCacheBean serialization(String json) throws JsonProcessingException {
        return super.getObjectMapper().readValue(json, DeptCacheBean.class);
    }

}
