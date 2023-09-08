package com.zclcs.common.export.excel.starter.kit;

import java.io.Serial;
import java.util.Map;

/**
 * @author zclcs
 * @since 2020/3/31
 */
public class ExcelReadException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<Integer, String> error;

    public ExcelReadException(String message, Map<Integer, String> error) {
        super(message);
        this.error = error;
    }

    public Map<Integer, String> getError() {
        return error;
    }
}
