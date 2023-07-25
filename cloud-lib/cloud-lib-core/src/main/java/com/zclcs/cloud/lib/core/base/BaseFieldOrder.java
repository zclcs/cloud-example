package com.zclcs.cloud.lib.core.base;

import lombok.Data;

/**
 * 分页实体
 *
 * @author zclcs
 */
@Data
public class BaseFieldOrder {

    /**
     * 排序字段
     */
    private String field;

    /**
     * 排序规则，asc升序，desc降序
     */
    private String order;
}
