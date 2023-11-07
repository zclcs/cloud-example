package com.zclcs.cloud.lib.dict.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.fegin.RemoteDictItemService;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class DictCache extends CacheService<List<DictItemCacheVo>> {

    private RemoteDictItemService remoteDictItemService;

    public DictCache() {
        super(RedisCachePrefix.DICT_PREFIX, true);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }


    @Override
    protected List<DictItemCacheVo> findByKey(Object... key) {
        return remoteDictItemService.findByDictName((String) key[0]);
    }

    @Override
    protected List<DictItemCacheVo> serialization(String json) {
        TypeReference<List<DictItemCacheVo>> valueTypeRef = new TypeReference<>() {
        };
        return JsonUtil.readValue(json, valueTypeRef);
    }

}
