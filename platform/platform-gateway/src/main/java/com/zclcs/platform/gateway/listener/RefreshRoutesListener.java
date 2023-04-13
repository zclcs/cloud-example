//package com.zclcs.platform.gateway.listener;
//
//import org.apache.commons.lang3.ObjectUtils;
//import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
//import org.springdoc.core.properties.SwaggerUiConfigParameters;
//import org.springdoc.core.properties.SwaggerUiConfigProperties;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.context.ApplicationListener;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * <p>Description: 服务变更监听 </p>
// *
// * @author : zhouchenglong
// */
//public class RefreshRoutesListener implements ApplicationListener<RefreshRoutesEvent> {
//
//    public static final String API_URI = "/v3/api-docs";
//    /**
//     * 网关应用名称
//     */
//    @Value("${spring.application.name}")
//    private String self;
//    private RouteLocator routeLocator;
//    private SwaggerUiConfigParameters swaggerUiConfigParameters;
//    private SwaggerUiConfigProperties swaggerUiConfigProperties;
//
//    public void setRouteLocator(RouteLocator routeLocator) {
//        this.routeLocator = routeLocator;
//    }
//
//    public void setSwaggerUiConfigParameters(SwaggerUiConfigParameters swaggerUiConfigParameters) {
//        this.swaggerUiConfigParameters = swaggerUiConfigParameters;
//    }
//
//    public void setSwaggerUiConfigProperties(SwaggerUiConfigProperties swaggerUiConfigProperties) {
//        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
//    }
//
//    @Override
//    public void onApplicationEvent(RefreshRoutesEvent refreshRoutesEvent) {
//        List<String> routes = new ArrayList<>();
//        routeLocator.getRoutes()
//                .filter(route -> route.getUri().getHost() != null && Objects.equals(route.getUri().getScheme(), "lb") && !self.equalsIgnoreCase(route.getUri().getHost()))
//                .subscribe(route -> routes.add(route.getUri().getHost()));
//        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = routes.stream().map(this::createSwaggerUrl).collect(Collectors.toSet());
//        if (ObjectUtils.isNotEmpty(swaggerUiConfigParameters)) {
//            swaggerUiConfigParameters.setUrls(swaggerUrls);
//            swaggerUiConfigProperties.setUrls(swaggerUrls);
//        }
//    }
//
//    private AbstractSwaggerUiConfigProperties.SwaggerUrl createSwaggerUrl(String service) {
//
//        String url = "/" + service.toLowerCase() + API_URI;
//
//        AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
//        swaggerUrl.setUrl(url);
//        swaggerUrl.setName(service);
//        return swaggerUrl;
//    }
//}
//
