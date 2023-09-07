package com.zclcs.common.export.excel.starter.annotation;

import com.zclcs.common.export.excel.starter.handler.ColumnDynamicSelectDataHandler;
import com.zclcs.common.export.excel.starter.handler.DefaultColumnDynamicSelectDataHandler;

import java.lang.annotation.*;

/**
 * 添加下拉列表注解
 * <p>
 * 支持静态，动态字符串数据源*
 * 以及二级级联下拉列表数据源*
 *
 * @author zclcs
 * @date 2022/11/7
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ExcelSelect {

    /**
     * 静态数据 只支持 字符串数组，如果要使用级联，请使用处理器获取数据
     */
    String[] staticData() default {};

    /**
     * 关联父列名称
     */
    String parentColumn() default "";

    /**
     * 动态数据
     */
    Class<? extends ColumnDynamicSelectDataHandler> handler() default DefaultColumnDynamicSelectDataHandler.class;

    /**
     * 主要是提供一些简单参数
     */
    String parameter() default "";

    /**
     * 设置下拉框的起始行，默认为第二行
     */
    int firstRow() default 1;

    /**
     * 设置下拉框的结束行，默认为最后一行
     */
    int lastRow() default 0x10000;
}
