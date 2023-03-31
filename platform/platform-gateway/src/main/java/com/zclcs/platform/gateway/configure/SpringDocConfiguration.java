package com.zclcs.platform.gateway.configure;

import com.zclcs.platform.gateway.listener.RefreshRoutesListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author zclcs
 * <p>
 * swagger 3.0 展示
 */
@Profile({"dev", "test"})
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SpringDocConfiguration {

    @Bean
    public RefreshRoutesListener refreshRoutesListener(RouteLocator routeLocator, SwaggerUiConfigParameters swaggerUiConfigParameters, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        RefreshRoutesListener refreshRoutesListener = new RefreshRoutesListener();
        refreshRoutesListener.setRouteLocator(routeLocator);
        refreshRoutesListener.setSwaggerUiConfigParameters(swaggerUiConfigParameters);
        refreshRoutesListener.setSwaggerUiConfigProperties(swaggerUiConfigProperties);
        log.trace("Bean [Refresh Routes Listener] in SpringDocConfiguration Auto Configure.");
        return refreshRoutesListener;
    }

}
