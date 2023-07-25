package com.zclcs;

import com.zclcs.cloud.lib.fegin.annotation.EnableMyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zclcs
 */
@EnableMyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class PlatformMaintenanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformMaintenanceApplication.class, args);
    }
}
