package com.zclcs.cloud.lib.dict.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.fegin.RemoteDictItemService;
import com.zclcs.common.redis.starter.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class DictValueCache extends CacheService<DictItemCacheVo> {

    private RemoteDictItemService remoteDictItemService;

    public DictValueCache() {
        super(RedisCachePrefix.DICT_VALUE_PREFIX, true);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }

    @Override
    protected DictItemCacheVo findByKey(Object... key) {
        return remoteDictItemService.findByDictNameAndValue((String) key[0], (String) key[1]);
    }

    @Override
    protected DictItemCacheVo serialization(String json) throws JsonProcessingException {
        return super.getObjectMapper().readValue(json, DictItemCacheVo.class);
    }

}
