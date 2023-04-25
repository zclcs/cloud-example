package com.zclcs;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zclcs
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PlatformGatewayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(PlatformGatewayApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }

}
