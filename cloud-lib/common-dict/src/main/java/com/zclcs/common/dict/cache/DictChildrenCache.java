package com.zclcs.common.dict.cache;

import com.zclcs.common.core.constant.RedisCachePrefix;
import com.zclcs.common.dict.entity.DictItem;
import com.zclcs.common.dict.fegin.RemoteDictItemService;
import com.zclcs.common.redis.starter.enums.CacheType;
import com.zclcs.common.redis.starter.service.CacheService;
import com.zclcs.common.redis.starter.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouc
 */
@Service
public class DictChildrenCache extends CacheService<List<DictItem>> {

    private RemoteDictItemService remoteDictItemService;

    public DictChildrenCache(RedisService redisService) {
        super(redisService, RedisCachePrefix.DICT_CHILDREN, CacheType.CACHE_NULL, null);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }


    @Override
    protected List<DictItem> findByKey(Object... key) {
        return remoteDictItemService.findByDictNameAndParentValue((String) key[0], (String) key[1]);
    }

}
