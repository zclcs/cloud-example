package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.Dept;
import com.zclcs.platform.system.api.fegin.RemoteDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class DeptCache extends CacheService<Dept> {

    private RemoteDeptService remoteDeptService;

    public DeptCache(RedisService redisService) {
        super(redisService, RedisCachePrefix.DEPT, CacheType.CACHE_USING_BLOOM_FILTER,
                redisService.getBloomFilter(RedisCachePrefix.BLOOM_FILTER_DEPT));
        super.init(10000, 0.03);
    }

    @Autowired
    public void setRemoteDeptService(RemoteDeptService remoteDeptService) {
        this.remoteDeptService = remoteDeptService;
    }

    @Override
    protected Dept findByKey(Object... key) {
        return remoteDeptService.findByDeptId((Long) key[0]);
    }

}
