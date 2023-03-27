package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.dict.core.json.annotation.DictValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典项 Ao
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
@Schema(title = "DictItemAo对象", description = "字典项")
public class DictItemAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "主键")
    private Long id;

    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "字典key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictName;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(description = "父级字典值")
    private String parentValue;

    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "值", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "标签", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Size(max = 2, message = "{noMoreThan}")
    @Schema(description = "字典类型 @@system_dict_item.type")
    @DictValid(value = "system_dict_item.type", message = "{dict}")
    private String type;

    @Size(max = 1, message = "{noMoreThan}")
    @Schema(description = "是否系统字典 @@yes_no")
    @DictValid(value = "yes_no", message = "{dict}")
    private String whetherSystemDict;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(description = "描述")
    private String description;

    @NotNull(message = "{required}")
    @Schema(description = "排序（升序）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sorted;

    @Size(max = 1, message = "{noMoreThan}")
    @Schema(description = "是否禁用 @@yes_no")
    @DictValid(value = "yes_no", message = "{dict}")
    private String isDisabled;


}