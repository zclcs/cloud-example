package com.zclcs.cloud.lib.dict.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.fegin.RemoteDictItemService;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class DictTitleCache extends CacheService<DictItemCacheVo> {

    private RemoteDictItemService remoteDictItemService;

    public DictTitleCache() {
        super(RedisCachePrefix.DICT_TITLE_PREFIX, true);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }

    @Override
    protected DictItemCacheVo findByKey(Object... key) {
        return remoteDictItemService.findByDictNameAndParentValueAndTitle((String) key[0], (String) key[1], (String) key[2]);
    }

    @Override
    protected DictItemCacheVo serialization(String json) {
        return JsonUtil.readValue(json, DictItemCacheVo.class);
    }

}
