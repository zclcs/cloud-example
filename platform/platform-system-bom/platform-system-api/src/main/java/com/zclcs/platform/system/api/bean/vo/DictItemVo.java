package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典项 Vo
 *
 * @author zclcs
 * @date 2023-03-06 10:56:41.301
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictItemVo extends BaseEntity implements Serializable {

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
    @DictText(value = "system_dict_item.type")
    private String type;

    /**
     * 是否系统字典 @@yes_no
     */
    @DictText(value = "yes_no")
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
    @DictText(value = "yes_no")
    private String isDisabled;


}