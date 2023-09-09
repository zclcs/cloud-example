package com.zclcs.cloud.lib.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zclcs
 */
@Getter
@AllArgsConstructor
public enum AreaType {

    /**
     * 省
     */
    PROVINCE("0000"),

    /**
     * 市
     */
    CITY("00"),


    /**
     * 区/县
     */
    COUNTY(""),
    ;

    /**
     *
     */
    private final String code;
}
