package com.zclcs.cloud.lib.core.base;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 分页实体
 *
 * @author zclcs
 */
@Data
public class BasePageAo {

    /**
     * 页数
     */
    @NotNull(message = "{required}")
    private Integer pageNum;

    /**
     * 页码
     */
    @NotNull(message = "{required}")
    private Integer pageSize;

    /**
     * 字段排序
     */
    private String fieldOrders;
}
