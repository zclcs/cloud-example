package com.zclcs.common.core.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 基础字段排序对象
 *
 * @author zclcs
 */
@Data
@Schema(description = "分页实体")
public class BaseFieldOrder {

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String field;

    /**
     * 排序规则，asc升序，desc降序
     */
    @Schema(description = "排序规则，asc升序，desc降序")
    private String order;
}
