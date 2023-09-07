package com.zclcs.common.export.excel.starter.bean;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zclcs.common.export.excel.starter.annotation.ExcelSelect;
import com.zclcs.common.export.excel.starter.handler.ColumnDynamicSelectDataHandler;
import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author zclcs
 * @date 2022/11/7
 */
@Data
public class ExcelSelectDataColumn<T> {

    private T source;

    private String column;

    private int columnIndex;

    private String parentColumn;

    private int parentColumnIndex;

    private int firstRow;

    private int lastRow;

    public T resolveSource(ExcelSelect excelSelect) {
        if (excelSelect == null) {
            return null;
        }
        // 获取固定下拉框的内容
        final String[] staticData = excelSelect.staticData();
        if (ArrayUtil.isNotEmpty(staticData)) {
            return (T) Arrays.asList(staticData);
        }
        // 获取动态下拉框的内容
        final Class<? extends ColumnDynamicSelectDataHandler> handlerClass = excelSelect.handler();
        if (Objects.nonNull(handlerClass)) {
            final ColumnDynamicSelectDataHandler handler = SpringUtil.getBean(handlerClass);
            return (T) handler.source(excelSelect.parameter()).apply(StrUtil.isNotEmpty(excelSelect.parameter()) ? excelSelect.parameter() : null);
        }
        return null;
    }
}
