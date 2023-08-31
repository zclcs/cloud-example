package com.zclcs.cloud.lib.dict.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.fegin.RemoteDictItemService;
import com.zclcs.common.redis.starter.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class DictChildrenCache extends CacheService<List<DictItemCacheVo>> {

    private RemoteDictItemService remoteDictItemService;

    public DictChildrenCache() {
        super(RedisCachePrefix.DICT_CHILDREN_PREFIX, true);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }


    @Override
    protected List<DictItemCacheVo> findByKey(Object... key) {
        return remoteDictItemService.findByDictNameAndParentValue((String) key[0], (String) key[1]);
    }

    @Override
    protected List<DictItemCacheVo> serialization(String json) throws JsonProcessingException {
        TypeReference<List<DictItemCacheVo>> valueTypeRef = new TypeReference<>() {
        };
        return super.getObjectMapper().readValue(json, valueTypeRef);
    }

}
