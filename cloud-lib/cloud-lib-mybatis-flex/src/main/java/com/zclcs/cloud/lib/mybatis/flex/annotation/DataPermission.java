package com.zclcs.cloud.lib.mybatis.flex.annotation;

import java.lang.annotation.*;

/**
 * 开启数据权限
 *
 * @author zclcs
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * 数据权限关联字段
     * 目前系统数据权限通过dept_id关联
     *
     * @return String
     */
    String value() default "dept_id";

}
