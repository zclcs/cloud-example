package com.zclcs;

import com.zclcs.cloud.lib.fegin.annotation.EnableMyFeignClients;
import com.zclcs.cloud.lib.security.annotation.EnableMyResourceServer;
import com.zclcs.common.doc.starter.annotation.EnableMyDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zclcs
 */
@EnableMyResourceServer
@EnableMyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableMyDoc
public class PlatformMaintenanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformMaintenanceApplication.class, args);
    }
}
