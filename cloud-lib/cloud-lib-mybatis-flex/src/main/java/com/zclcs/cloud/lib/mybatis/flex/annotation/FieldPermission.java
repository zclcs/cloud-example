package com.zclcs.cloud.lib.mybatis.flex.annotation;

import java.lang.annotation.*;

/**
 * 开启字段权限
 *
 * @author zclcs
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldPermission {

    /**
     * 需要字段权限的属性
     */
    String[] value() default {};

}
