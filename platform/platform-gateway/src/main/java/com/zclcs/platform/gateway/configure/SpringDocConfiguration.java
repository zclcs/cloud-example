package com.zclcs.platform.gateway.configure;

import com.zclcs.platform.gateway.properties.GatewayConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zclcs
 * <p>
 * swagger 3.0 展示
 */
@Profile({"dev", "test"})
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SpringDocConfiguration implements InitializingBean {

    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    private final GatewayConfigProperties gatewayConfigProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrlSet = new HashSet<>();
        AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
        Map<String, String> services = gatewayConfigProperties.getServices();
        for (String serviceName : services.keySet()) {
            swaggerUrl.setName(serviceName);
            swaggerUrl.setUrl(services.get(serviceName) + "/v3/api-docs");
            swaggerUrlSet.add(swaggerUrl);
        }
        swaggerUiConfigProperties.setUrls(swaggerUrlSet);
    }

}
