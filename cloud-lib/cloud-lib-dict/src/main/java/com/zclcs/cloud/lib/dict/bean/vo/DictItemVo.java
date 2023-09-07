package com.zclcs.cloud.lib.dict.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典 Vo
 *
 * @author zclcs
 * @since 2023-09-01 20:03:54.686
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictItemVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * 默认值：
     */
    private Long id;

    /**
     * 字典key
     * 默认值：
     */
    private String dictName;

    /**
     * 父级字典值
     * 默认值：0
     */
    private String parentValue;

    /**
     * 值
     * 默认值：
     */
    private String value;

    /**
     * 标签
     * 默认值：
     */
    private String title;

    /**
     * 字典类型 @@system_dict_item.type
     * 默认值：0
     */
    private String type;

    /**
     * 字典类型 @@system_dict_item.type
     */
    private String typeText;

    public String getTypeText() {
        return DictCacheUtil.getDictTitle("system_dict_item.type", this.type);
    }

    /**
     * 是否系统字典 @@yes_no
     * 默认值：1
     */
    private String whetherSystemDict;

    /**
     * 是否系统字典 @@yes_no
     */
    private String whetherSystemDictText;

    public String getWhetherSystemDictText() {
        return DictCacheUtil.getDictTitle("yes_no", this.whetherSystemDict);
    }

    /**
     * 描述
     * 默认值：
     */
    private String description;

    /**
     * 排序（升序）
     * 默认值：0
     */
    private Integer sorted;

    /**
     * 是否禁用 @@yes_no
     * 默认值：0
     */
    private String isDisabled;

    /**
     * 是否禁用 @@yes_no
     */
    private String isDisabledText;

    public String getIsDisabledText() {
        return DictCacheUtil.getDictTitle("yes_no", this.isDisabled);
    }


}