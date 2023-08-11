package com.zclcs.cloud.lib.dict.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheBean;
import com.zclcs.cloud.lib.dict.fegin.RemoteDictItemService;
import com.zclcs.common.redis.starter.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class DictCache extends CacheService<List<DictItemCacheBean>> {

    private RemoteDictItemService remoteDictItemService;

    public DictCache() {
        super(RedisCachePrefix.DICT, true);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }


    @Override
    protected List<DictItemCacheBean> findByKey(Object... key) {
        return remoteDictItemService.findByDictName((String) key[0]);
    }

    @Override
    protected List<DictItemCacheBean> serialization(String json) throws JsonProcessingException {
        TypeReference<List<DictItemCacheBean>> valueTypeRef = new TypeReference<>() {
        };
        return super.getObjectMapper().readValue(json, valueTypeRef);
    }

}
