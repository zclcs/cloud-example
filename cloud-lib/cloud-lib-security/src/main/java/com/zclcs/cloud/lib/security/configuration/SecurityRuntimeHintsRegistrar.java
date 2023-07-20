package com.zclcs.cloud.lib.security.configuration;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

/**
 * mica-ip2region native 支持
 *
 * @author zclcs
 */
class SecurityRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources()
                .registerPattern("i18n/errors/messages_zh_CN.properties");
    }

}
