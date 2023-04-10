package com.zclcs.common.dict.core.cache;

import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.core.enums.CacheType;
import com.zclcs.common.dict.core.entity.DictItem;
import com.zclcs.common.dict.core.fegin.RemoteDictItemService;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouc
 */
@Service
public class DictCache extends CacheService<List<DictItem>> {

    private RemoteDictItemService remoteDictItemService;

    public DictCache(RedisService redisService) {
        super(redisService, RedisCachePrefixConstant.DICT, CacheType.CACHE_NULL, null);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }


    @Override
    protected List<DictItem> findByKey(Object... key) {
        return remoteDictItemService.findByDictName((String) key[0]);
    }

}
