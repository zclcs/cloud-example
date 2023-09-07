package com.zclcs.common.export.excel.starter.handler;

import java.util.List;
import java.util.function.Function;

/**
 * @author zclcs
 * @date 2022/11/7
 */
public class DefaultColumnDynamicSelectDataHandler implements ColumnDynamicSelectDataHandler<String, List<String>> {

    @Override
    public Function<String, List<String>> source(String dictName) {
        return null;
    }
}
