package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
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

    public DeptCache(RedisService redisService) {
        super(RedisCachePrefix.DEPT_PREFIX, redisService.getBloomFilter(RedisCachePrefix.BLOOM_FILTER_DEPT_PREFIX), 10000, 0.03);
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
    protected DeptCacheVo serialization(String json) {
        return JsonUtil.readValue(json, DeptCacheVo.class);
    }

}
