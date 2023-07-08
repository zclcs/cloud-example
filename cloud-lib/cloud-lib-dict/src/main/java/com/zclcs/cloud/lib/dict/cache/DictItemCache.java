package com.zclcs.cloud.lib.dict.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheBean;
import com.zclcs.cloud.lib.dict.fegin.RemoteDictItemService;
import com.zclcs.common.redis.starter.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zclcs
 */
@Service
public class DictItemCache extends CacheService<DictItemCacheBean> {

    private RemoteDictItemService remoteDictItemService;

    public DictItemCache() {
        super(RedisCachePrefix.DICT_ITEM);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }

    @Override
    protected DictItemCacheBean findByKey(Object... key) {
        return remoteDictItemService.findByDictNameAndValue((String) key[0], (String) key[1]);
    }

}
