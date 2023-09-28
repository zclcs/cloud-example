package com.zclcs.cloud.lib.core.configure;

import cn.hutool.core.util.ClassUtil;
import com.zclcs.cloud.lib.core.properties.GlobalProperties;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.cloud.lib.core.properties.RabbitmqApiInfoProperties;
import com.zclcs.cloud.lib.core.properties.SaTokenProperties;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.Set;

/**
 * @author zclcs
 */
@AutoConfiguration
@EnableConfigurationProperties(value = {
        MyNacosProperties.class,
        GlobalProperties.class,
        RabbitmqApiInfoProperties.class,
        SaTokenProperties.class
})
@ImportRuntimeHints({MyCoreAutoConfigure.CoreRuntimeHintsRegistrar.class})
public class MyCoreAutoConfigure {

    static class CoreRuntimeHintsRegistrar implements RuntimeHintsRegistrar {
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
}
