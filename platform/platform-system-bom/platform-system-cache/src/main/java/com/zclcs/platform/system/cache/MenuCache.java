package com.zclcs.platform.system.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.bean.cache.MenuCacheBean;
import com.zclcs.platform.system.api.fegin.RemoteMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
@Slf4j
@Service
public class MenuCache extends CacheService<MenuCacheBean> {

    private RemoteMenuService remoteMenuService;

    public MenuCache(RedisService redisService) {
        super(RedisCachePrefix.MENU, false, redisService.getBloomFilter(RedisCachePrefix.BLOOM_FILTER_MENU), 10000, 0.03);
    }

    @Autowired
    public void setRemoteMenuService(RemoteMenuService remoteMenuService) {
        this.remoteMenuService = remoteMenuService;
    }

    @Override
    protected MenuCacheBean findByKey(Object... key) {
        return remoteMenuService.findByMenuId((Long) key[0]);
    }

    @Override
    protected MenuCacheBean serialization(String json) throws JsonProcessingException {
        return super.getObjectMapper().readValue(json, MenuCacheBean.class);
    }

    public List<MenuCacheBean> getMenusByMenuIds(List<Long> menuIds) {
        List<MenuCacheBean> cacheMenu = new ArrayList<>();
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
            Map<Long, MenuCacheBean> byMenuIds = remoteMenuService.findByMenuIds(needCacheMenuId);
            for (Map.Entry<Long, MenuCacheBean> longMenuEntry : byMenuIds.entrySet()) {
                cacheMenu.add(cacheKey(longMenuEntry.getValue(), longMenuEntry.getKey()));
            }
        }
        for (Long aLong : alreadyCacheMenuId) {
            cacheMenu.add(findCache(aLong));
        }
        return cacheMenu;
    }
}
