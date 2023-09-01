package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典 Ao
 *
 * @author zclcs
 * @since 2023-09-01 20:03:54.686
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
     * 默认值：
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    /**
     * 字典key
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String dictName;

    /**
     * 父级字典值
     * 默认值：0
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String parentValue;

    /**
     * 值
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String value;

    /**
     * 标签
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String title;

    /**
     * 字典类型 @@system_dict_item.type
     * 默认值：0
     */
    @DictValid(value = "system_dict_item.type", message = "{dict}")
    @Size(max = 2, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String type;

    /**
     * 是否系统字典 @@yes_no
     * 默认值：1
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String whetherSystemDict;

    /**
     * 描述
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String description;

    /**
     * 排序（升序）
     * 默认值：0
     */
    @NotNull(message = "{required}")
    private Integer sorted;

    /**
     * 是否禁用 @@yes_no
     * 默认值：0
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    private String isDisabled;


}