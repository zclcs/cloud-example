package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
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
 * @since 2023-03-06 10:56:41.301
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictItemAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    /**
     * 字典key
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String dictName;

    /**
     * 父级字典值
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String parentValue;

    /**
     * 值
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String value;

    /**
     * 标签
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String title;

    /**
     * 字典类型 @@system_dict_item.type
     */
    @Size(max = 2, message = "{noMoreThan}")
    @DictValid(value = "system_dict_item.type", message = "{dict}")
    private String type;

    /**
     * 是否系统字典 @@yes_no
     */
    @Size(max = 1, message = "{noMoreThan}")
    @DictValid(value = "yes_no", message = "{dict}")
    private String whetherSystemDict;

    /**
     * 描述
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String description;

    /**
     * 排序（升序）
     */
    @NotNull(message = "{required}")
    private Integer sorted;

    /**
     * 是否禁用 @@yes_no
     */
    @Size(max = 1, message = "{noMoreThan}")
    @DictValid(value = "yes_no", message = "{dict}")
    private String isDisabled;


}