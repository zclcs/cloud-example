package com.zclcs.cloud.lib.mybatis.plus.configure;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

/**
 * mica-ip2region native 支持
 *
 * @author zclcs
 */
class MyBatisPlusRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources()
                .registerPattern("spy.properties");
    }

}
