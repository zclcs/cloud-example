package com.zclcs;

import com.zclcs.cloud.lib.fegin.annotation.EnableMyFeignClients;
import com.zclcs.cloud.lib.security.annotation.EnableMyResourceServer;
import com.zclcs.common.doc.starter.annotation.EnableMyDoc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zclcs
 */
@EnableAsync
@EnableMyResourceServer
@EnableMyFeignClients
@EnableDiscoveryClient
@MapperScan("com.zclcs.platform.maintenance.mapper")
@SpringBootApplication
@EnableMyDoc
public class PlatformMaintenanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformMaintenanceApplication.class, args);
    }
}
