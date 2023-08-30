package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.bean.Tree;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典树
 *
 * @author zclcs
 * @since 2023-03-06 10:56:41.301
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictItemTreeVo extends Tree<DictItemVo> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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


}