package com.zclcs.cloud.lib.dict.utils;

import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.cache.DictCache;
import com.zclcs.cloud.lib.dict.cache.DictChildrenCache;
import com.zclcs.cloud.lib.dict.cache.DictItemCache;
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

    public static List<DictItemCacheVo> getDictByDictName(String dictName) {
        return DICT_CACHE.findCache(dictName);
    }

    public static void deleteDictByDictName(String dictName) {
        DICT_CACHE.deleteCache(dictName);
    }

    public static void deleteDictByDictNames(Object... dictNames) {
        DICT_CACHE.deleteCache(dictNames);
    }

    private static DictItemCacheVo getDictItemByDictNameAndValue(String dictName, String value) {
        return DICT_ITEM_CACHE.findCache(dictName, value);
    }

    public static void deleteDictItemByDictNameAndValue(String dictName, String value) {
        DICT_ITEM_CACHE.deleteCache(dictName, value);
    }

    public static void deleteDictItemByDictNamesAndValues(List<List<Object>> keys) {
        DICT_ITEM_CACHE.deleteCache(keys);
    }

    public static List<DictItemCacheVo> getDictByDictNameAndParentValue(String dictName, String parentValue) {
        return DICT_CHILDREN_CACHE.findCache(dictName, parentValue);
    }

    public static void deleteDictByDictNameAndParentValue(String dictName, String parentValue) {
        DICT_CHILDREN_CACHE.deleteCache(dictName, parentValue);
    }

    public static void deleteDictByDictNamesAndParentValues(List<List<Object>> keys) {
        DICT_CHILDREN_CACHE.deleteCache(keys);
    }

    public static DictItemCacheVo getDict(String dictName, String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return getDictItemByDictNameAndValue(dictName, value);
    }

    public static String getDictTitle(String dictName, String value) {
        if (StrUtil.isBlank(value)) {
            return "";
        }
        DictItemCacheVo dictItemByDictNameAndValue = getDictItemByDictNameAndValue(dictName, value);
        return dictItemByDictNameAndValue == null ? "" : dictItemByDictNameAndValue.getTitle();
    }

    public static String getDictTitleArray(String dictName, String value) {
        return getDictTitleArray(dictName, value, Strings.COMMA, Strings.SLASH, false);
    }

    /**
     * @param dictName   字典名称
     * @param value      字典code
     * @param separator  字典code分隔符
     * @param join       字典文本分隔符
     * @param ignoreNull 是否忽略字典文本为 null 的数据。true：忽略，跳过；false：不忽略，输出 '' 字符串
     * @return 字典文本
     */
    public static String getDictTitleArray(String dictName, String value, String separator, String join, boolean ignoreNull) {
        if (StrUtil.isBlank(value)) {
            return "";
        }
        List<String> split = StrUtil.split(value, separator);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            String dictTitle = getDictTitle(dictName, s);
            if (StrUtil.isBlank(dictTitle) && ignoreNull) {
                continue;
            }
            sb.append(dictTitle).append(join);
        }
        String finalDictTitle = sb.toString();
        return StrUtil.isBlank(finalDictTitle) ? finalDictTitle : finalDictTitle.substring(0, finalDictTitle.length() - 1);
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
