package com.zclcs.platform.system.cache;

import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.Dept;
import com.zclcs.platform.system.api.fegin.RemoteDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouc
 */
@Service
public class DeptCache extends CacheService<Dept> {

    private RemoteDeptService remoteDeptService;

    public DeptCache(RedisService redisService) {
        super(redisService, RedisCachePrefixConstant.DEPT, CacheType.CACHE_USING_BLOOM_FILTER,
                redisService.getBloomFilter(RedisCachePrefixConstant.BLOOM_FILTER_DEPT));
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
