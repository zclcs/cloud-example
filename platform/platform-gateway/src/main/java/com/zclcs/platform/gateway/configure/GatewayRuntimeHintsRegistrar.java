package com.zclcs.platform.gateway.configure;

import com.alibaba.cloud.nacos.NacosServiceInstance;
import lombok.SneakyThrows;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

/**
 * mica-ip2region native 支持
 *
 * @author zclcs
 */
public class GatewayRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @SneakyThrows
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        
        hints.reflection().registerType(NacosServiceInstance.class, MemberCategory.values());
    }

}
