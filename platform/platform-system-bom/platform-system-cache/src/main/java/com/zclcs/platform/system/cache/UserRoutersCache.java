package com.zclcs.platform.system.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.platform.system.api.bean.router.VueRouter;
import com.zclcs.platform.system.api.bean.vo.MenuVo;
import com.zclcs.platform.system.api.fegin.RemoteMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class UserRoutersCache extends CacheService<List<VueRouter<MenuVo>>> {

    private RemoteMenuService remoteMenuService;

    public UserRoutersCache() {
        super(RedisCachePrefix.USER_ROUTERS_PREFIX);
    }

    @Autowired
    public void setRemoteMenuService(RemoteMenuService remoteMenuService) {
        this.remoteMenuService = remoteMenuService;
    }

    @Override
    protected List<VueRouter<MenuVo>> findByKey(Object... key) {
        return remoteMenuService.findUserRouters((String) key[0]);
    }

    @Override
    protected List<VueRouter<MenuVo>> serialization(String json) {
        TypeReference<List<VueRouter<MenuVo>>> valueTypeRef = new TypeReference<>() {
        };
        return JsonUtil.readValue(json, valueTypeRef);
    }
}
