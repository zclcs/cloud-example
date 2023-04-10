package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.common.core.base.Tree;
import com.zclcs.common.dict.core.json.annotation.DictText;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "DictItemTreeVo", description = "字典树")
public class DictItemTreeVo extends Tree<DictItemVo> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典类型 @@system_dict_item.type")
    @DictText(value = "system_dict_item.type")
    private String type;

    @Schema(description = "是否系统字典 @@yes_no")
    @DictText(value = "yes_no")
    private String whetherSystemDict;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "排序（升序）")
    private Integer sorted;


}