package com.zclcs.platform.system.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.fegin.RemoteMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class MenuCache extends CacheService<Menu> {

    private RemoteMenuService remoteMenuService;

    public MenuCache(RedisService redisService) {
        super(redisService, RedisCachePrefix.MENU, CacheType.CACHE_USING_BLOOM_FILTER,
                redisService.getBloomFilter(RedisCachePrefix.BLOOM_FILTER_MENU));
        super.init(10000, 0.03);
    }

    @Autowired
    public void setRemoteMenuService(RemoteMenuService remoteMenuService) {
        this.remoteMenuService = remoteMenuService;
    }

    @Override
    protected Menu findByKey(Object... key) {
        return remoteMenuService.findByMenuId((Long) key[0]);
    }
}
