package com.zclcs.platform.system.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.fegin.RemoteMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
@Service
public class MenuCache extends CacheService<Menu> {

    private RemoteMenuService remoteMenuService;

    public MenuCache(RedisService redisService) {
        super(RedisCachePrefix.MENU, redisService.getBloomFilter(RedisCachePrefix.BLOOM_FILTER_MENU), 10000, 0.03);
    }

    @Autowired
    public void setRemoteMenuService(RemoteMenuService remoteMenuService) {
        this.remoteMenuService = remoteMenuService;
    }

    @Override
    protected Menu findByKey(Object... key) {
        return remoteMenuService.findByMenuId((Long) key[0]);
    }

    public List<Menu> getMenusByMenuIds(List<Long> menuIds) {
        List<Menu> cacheMenu = new ArrayList<>();
        List<Long> needCacheMenuId = new ArrayList<>();
        List<Long> alreadyCacheMenuId = new ArrayList<>();
        for (Long menuId : menuIds) {
            if (!checkCache(menuId)) {
                needCacheMenuId.add(menuId);
            } else {
                alreadyCacheMenuId.add(menuId);
            }
        }
        if (CollectionUtil.isNotEmpty(needCacheMenuId)) {
            Map<Long, Menu> byMenuIds = remoteMenuService.findByMenuIds(needCacheMenuId);
            for (Map.Entry<Long, Menu> longMenuEntry : byMenuIds.entrySet()) {
                cacheMenu.add(cacheKey(longMenuEntry.getValue(), longMenuEntry.getKey()));
            }
        }
        for (Long aLong : alreadyCacheMenuId) {
            cacheMenu.add(findCache(aLong));
        }
        return cacheMenu;
    }
}
