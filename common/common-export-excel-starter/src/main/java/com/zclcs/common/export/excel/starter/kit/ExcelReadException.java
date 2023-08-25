package com.zclcs.common.export.excel.starter.kit;

import com.alibaba.excel.metadata.data.CellData;

import java.io.Serial;
import java.util.Map;

/**
 * @author zclcs
 * @date 2020/3/31
 */
public class ExcelReadException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<Integer, CellData<?>> error;

    public ExcelReadException(String message, Map<Integer, CellData<?>> error) {
        super(message);
        this.error = error;
    }

    public Map<Integer, CellData<?>> getError() {
        return error;
    }
}
