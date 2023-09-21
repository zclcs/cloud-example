package com.zclcs.platform.system.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.bean.cache.MenuCacheVo;
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
public class MenuCache extends CacheService<MenuCacheVo> {

    private RemoteMenuService remoteMenuService;

    public MenuCache() {
        super(RedisCachePrefix.MENU_PREFIX);
    }

    @Autowired
    public void setRemoteMenuService(RemoteMenuService remoteMenuService) {
        this.remoteMenuService = remoteMenuService;
    }

    @Override
    protected MenuCacheVo findByKey(Object... key) {
        return remoteMenuService.findByMenuId((Long) key[0]);
    }

    @Override
    protected MenuCacheVo serialization(String json) throws JsonProcessingException {
        return super.getObjectMapper().readValue(json, MenuCacheVo.class);
    }

    public List<MenuCacheVo> getMenusByMenuIds(List<Long> menuIds) {
        List<MenuCacheVo> cacheMenu = new ArrayList<>();
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
            Map<Long, MenuCacheVo> byMenuIds = remoteMenuService.findByMenuIds(needCacheMenuId);
            for (Map.Entry<Long, MenuCacheVo> longMenuEntry : byMenuIds.entrySet()) {
                cacheMenu.add(cacheKey(longMenuEntry.getValue(), longMenuEntry.getKey()));
            }
        }
        for (Long aLong : alreadyCacheMenuId) {
            cacheMenu.add(findCache(aLong));
        }
        return cacheMenu;
    }
}
