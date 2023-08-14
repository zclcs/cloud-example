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
    private Long pageNum;

    /**
     * 页码
     */
    @NotNull(message = "{required}")
    private Long pageSize;
}
