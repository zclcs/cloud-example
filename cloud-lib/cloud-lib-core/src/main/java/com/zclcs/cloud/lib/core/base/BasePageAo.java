package com.zclcs.cloud.lib.core.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 基础分页对象
 *
 * @author zclcs
 */
@Data
@Schema(title = "BasePageAo", description = "分页实体")
public class BasePageAo {

    /**
     * 页码
     */
    @NotNull(message = "请输入页码")
    @Schema(description = "页码", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageNum;

    /**
     * 页数
     */
    @NotNull(message = "请输入页数")
    @Schema(description = "页数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageSize;

    @Schema(description = "字段排序")
    private List<BaseFieldOrder> fieldOrders;
}
