package com.zclcs.cloud.lib.core.configure;

import cn.hutool.core.util.ClassUtil;
import lombok.SneakyThrows;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

import java.util.Set;

/**
 * mica-ip2region native 支持
 *
 * @author zclcs
 */
public class CoreRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @SneakyThrows
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        Set<Class<?>> classes = ClassUtil.scanPackage("com.zclcs");
        for (Class<?> aClass : classes) {
            hints.reflection().registerType(aClass, MemberCategory.values());
        }
        hints.resources()
                .registerPattern("mapper/*")
                .registerPattern("generator/templates/*")
                .registerPattern("templates/*")
                .registerPattern("sql/*")
        ;
    }

}
