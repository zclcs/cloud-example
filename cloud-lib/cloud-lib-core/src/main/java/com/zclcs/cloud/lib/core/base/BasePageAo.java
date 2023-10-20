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
     * 页码
     * 默认：1
     */
    @NotNull(message = "{required}")
    private Long pageNum = 1L;

    /**
     * 页数
     * 默认：10
     */
    @NotNull(message = "{required}")
    private Long pageSize = 10L;
}
