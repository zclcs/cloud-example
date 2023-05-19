package com.zclcs.cloud.lib.dict.cache;

import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.dict.entity.DictItem;
import com.zclcs.cloud.lib.dict.fegin.RemoteDictItemService;
import com.zclcs.common.redis.starter.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service
public class DictChildrenCache extends CacheService<List<DictItem>> {

    private RemoteDictItemService remoteDictItemService;

    public DictChildrenCache() {
        super(RedisCachePrefix.DICT_CHILDREN);
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
