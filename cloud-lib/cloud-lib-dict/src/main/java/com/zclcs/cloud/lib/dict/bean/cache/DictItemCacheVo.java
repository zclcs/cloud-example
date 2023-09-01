package com.zclcs.cloud.lib.dict.bean.cache;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 20:03:54.686
 */
@Data
public class DictItemCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

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
     * 字典类型 @@system_dict_item.type
     */
    private String type;

    /**
     * 是否系统字典 @@yes_no
     */
    private String whetherSystemDict;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private Integer sorted;

    /**
     * 是否禁用 @@yes_no
     */
    private String isDisabled;

}