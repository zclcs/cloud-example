package com.zclcs.common.dict.utils;

import com.zclcs.common.dict.cache.DictCache;
import com.zclcs.common.dict.cache.DictChildrenCache;
import com.zclcs.common.dict.cache.DictItemCache;
import com.zclcs.common.dict.entity.DictItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zclcs
 */
@Component
public class DictCacheUtil {
    private static DictCache DICT_CACHE;
    private static DictItemCache DICT_ITEM_CACHE;
    private static DictChildrenCache DICT_CHILDREN_CACHE;
    private DictCache dictCache;
    private DictItemCache dictItemCache;
    private DictChildrenCache dictChildrenCache;

    public static List<DictItem> getDictByDictName(String dictName) {
        return DICT_CACHE.findCache(dictName);
    }

    public static void deleteDictByDictName(String dictName) {
        DICT_CACHE.deleteCache(dictName);
    }

    public static void deleteDictByDictNames(Object... dictNames) {
        DICT_CACHE.deleteCache(dictNames);
    }

    public static DictItem getDictItemByDictNameAndValue(String dictName, String value) {
        return DICT_ITEM_CACHE.findCache(dictName, value);
    }

    public static void deleteDictItemByDictNameAndValue(String dictName, String value) {
        DICT_ITEM_CACHE.deleteCache(dictName, value);
    }

    public static void deleteDictItemByDictNamesAndValues(List<List<Object>> keys) {
        DICT_ITEM_CACHE.deleteCache(keys);
    }

    public static List<DictItem> getDictByDictNameAndParentValue(String dictName, String parentValue) {
        return DICT_CHILDREN_CACHE.findCache(dictName, parentValue);
    }

    public static void deleteDictByDictNameAndParentValue(String dictName, String parentValue) {
        DICT_CHILDREN_CACHE.deleteCache(dictName, parentValue);
    }

    public static void deleteDictByDictNamesAndParentValues(List<List<Object>> keys) {
        DICT_CHILDREN_CACHE.deleteCache(keys);
    }

    @Autowired(required = false)
    public void setDictCache(DictCache dictCache) {
        this.dictCache = dictCache;
    }

    @Autowired(required = false)
    public void setDictItemCache(DictItemCache dictItemCache) {
        this.dictItemCache = dictItemCache;
    }

    @Autowired(required = false)
    public void setDictChildrenCache(DictChildrenCache dictChildrenCache) {
        this.dictChildrenCache = dictChildrenCache;
    }

    @PostConstruct
    public void init() {
        DICT_CACHE = dictCache;
        DICT_ITEM_CACHE = dictItemCache;
        DICT_CHILDREN_CACHE = dictChildrenCache;
    }
}
