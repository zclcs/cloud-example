package com.zclcs.cloud.lib.mybatis.flex.annotation;

import java.lang.annotation.*;

/**
 * 开启数据脱敏
 *
 * @author zclcs
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataDesensitization {

    /**
     * 需要脱敏的属性
     */
    String[] value() default {};

}
