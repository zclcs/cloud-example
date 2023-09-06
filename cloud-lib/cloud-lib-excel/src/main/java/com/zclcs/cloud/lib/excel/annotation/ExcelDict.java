package com.zclcs.cloud.lib.excel.annotation;

import java.lang.annotation.*;

/**
 * 开启字段权限
 *
 * @author zclcs
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelDict {

    /**
     * 需要字段权限的属性
     */
    String value();

    boolean isTree() default false;

    int treeDeep() default 3;

}
