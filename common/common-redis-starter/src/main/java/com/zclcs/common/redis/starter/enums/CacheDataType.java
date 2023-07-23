package com.zclcs.common.redis.starter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zclcs
 */
@Getter
@AllArgsConstructor
public enum CacheDataType {

    /**
     * 普通bean
     */
    BEAN(1, "普通bean"),
    /**
     * 集合
     */
    COLLECTION(2, "集合"),
    ;

    private final Integer value;
    private final String title;
}
