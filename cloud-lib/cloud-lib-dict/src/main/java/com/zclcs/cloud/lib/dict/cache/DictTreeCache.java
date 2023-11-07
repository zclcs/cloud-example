package com.zclcs.cloud.lib.dict.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.dict.bean.vo.DictItemTreeVo;
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
public class DictTreeCache extends CacheService<List<DictItemTreeVo>> {

    private RemoteDictItemService remoteDictItemService;

    public DictTreeCache() {
        super(RedisCachePrefix.DICT_TREE_PREFIX, true);
    }

    @Autowired(required = false)
    public void setRemoteDictItemService(RemoteDictItemService remoteDictItemService) {
        this.remoteDictItemService = remoteDictItemService;
    }


    @Override
    protected List<DictItemTreeVo> findByKey(Object... key) {
        return remoteDictItemService.tree((String) key[0]);
    }

    @Override
    protected List<DictItemTreeVo> serialization(String json) {
        TypeReference<List<DictItemTreeVo>> valueTypeRef = new TypeReference<>() {
        };
        return JsonUtil.readValue(json, valueTypeRef);
    }

}
