package com.zclcs.common.export.excel.starter.kit;

import java.io.Serial;

/**
 * @author zclcs
 * @date 2020/3/31
 */
public class ExcelException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ExcelException(String message) {
        super(message);
    }

}
