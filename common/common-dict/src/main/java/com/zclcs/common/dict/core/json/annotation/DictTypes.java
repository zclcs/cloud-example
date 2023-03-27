package com.zclcs.common.dict.core.json.annotation;

import java.lang.annotation.*;

/**
 * 用在系统枚举对象上：标记该枚举的内容信息。
 * 一个枚举可做成多个字典信息，可以通过重复使用 {@link DictType} 注解把多个系统枚举合并到一个字典中
 *
 * @author zclcs
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictTypes {
    DictType[] value() default {};
}