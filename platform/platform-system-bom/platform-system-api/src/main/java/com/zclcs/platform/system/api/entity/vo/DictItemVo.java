package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.common.core.base.BaseEntity;
import com.zclcs.common.dict.json.annotation.DictText;
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
@Schema(title = "DictItemVo对象", description = "字典项")
public class DictItemVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "字典key")
    private String dictName;

    @Schema(description = "父级字典值")
    private String parentValue;

    @Schema(description = "值")
    private String value;

    @Schema(description = "标签")
    private String title;

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

    @Schema(description = "是否禁用 @@yes_no")
    @DictText(value = "yes_no")
    private String isDisabled;


}