package com.zclcs.cloud.lib.dict.bean.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典项缓存
 *
 * @author zclcs
 * @since 2023-03-06 10:56:41.301
 */
@Data
public class DictItemCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典key
     */
    private String dictName;

    /**
     * 父级字典值
     */
    private String parentValue;

    /**
     * 值
     */
    private String value;

    /**
     * 标签
     */
    private String title;

    /**
     * 是否禁用
     */
    private Boolean disabled;

    public static List<DictItemCacheVo> convertToDictItemCacheBeanList(List<DictItem> dictItems) {
        List<DictItemCacheVo> dictItemCacheVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(dictItems)) {
            for (DictItem dictItem : dictItems) {
                dictItemCacheVos.add(convertToDictItemCacheBean(dictItem));
            }
            return dictItemCacheVos;
        }
        return dictItemCacheVos;
    }

    public static DictItemCacheVo convertToDictItemCacheBean(DictItem item) {
        if (item == null) {
            return null;
        }
        DictItemCacheVo result = new DictItemCacheVo();
        result.setDictName(item.getDictName());
        result.setParentValue(item.getParentValue());
        result.setValue(item.getValue());
        result.setTitle(item.getTitle());
        result.setDisabled(Dict.YES_NO_1.equals(item.getIsDisabled()));
        return result;
    }
}