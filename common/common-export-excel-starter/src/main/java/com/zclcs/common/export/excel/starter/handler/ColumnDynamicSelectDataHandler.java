package com.zclcs.common.export.excel.starter.handler;

import java.util.function.Function;

/**
 * @author zclcs
 * @date 2022/11/7
 */
public interface ColumnDynamicSelectDataHandler<T, R> {

    /**
     * 获取数据
     *
     * @return 数据
     */
    Function<T, R> source(String dictName);
}
