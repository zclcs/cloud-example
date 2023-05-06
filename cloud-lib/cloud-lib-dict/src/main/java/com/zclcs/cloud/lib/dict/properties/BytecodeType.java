package com.zclcs.cloud.lib.dict.properties;

import com.zclcs.cloud.lib.dict.bytecode.IDictConverterGenerate;

/**
 * 字节码技术类型
 *
 * @author zclcs
 * @since 1.4.8
 */
public enum BytecodeType {
    /**
     * 不设定字节码技术。需要手动向容器中注册 {@link IDictConverterGenerate } 对象。
     */
    NONE,
    /**
     * Spring ASM 字节码技术
     */
    ASM,
    /**
     * javassist 字节码技术
     */
    JAVASSIST;
}
