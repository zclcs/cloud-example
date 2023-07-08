package com.zclcs.cloud.lib.dict.bean.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.dict.bean.entity.DictItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典项 Entity
 *
 * @author zclcs
 * @date 2023-03-06 10:56:41.301
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "DictItem缓存对象", description = "字典项缓存")
public class DictItemCacheBean implements Serializable {

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

    public static List<DictItemCacheBean> convertToDictItemCacheBeanList(List<DictItem> dictItems) {
        List<DictItemCacheBean> dictItemCacheBeans = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(dictItems)) {
            for (DictItem dictItem : dictItems) {
                dictItemCacheBeans.add(convertToDictItemCacheBean(dictItem));
            }
            return dictItemCacheBeans;
        }
        return dictItemCacheBeans;
    }

    public static DictItemCacheBean convertToDictItemCacheBean(DictItem item) {
        if (item == null) {
            return null;
        }
        DictItemCacheBean result = new DictItemCacheBean();
        result.setDictName(item.getDictName());
        result.setParentValue(item.getParentValue());
        result.setValue(item.getValue());
        result.setTitle(item.getTitle());
        result.setDisabled(Dict.YES_NO_1.equals(item.getIsDisabled()));
        return result;
    }
}