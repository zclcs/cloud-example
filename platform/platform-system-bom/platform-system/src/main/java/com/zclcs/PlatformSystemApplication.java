package com.zclcs;

import com.zclcs.cloud.lib.fegin.annotation.EnableMyFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zclcs
 */
@EnableAsync
@EnableMyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(value = "com.zclcs.platform.system.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class PlatformSystemApplication {

    public static void main(String[] args) {
        System.setProperty("javax.xml.accessExternalDTD", "all");
        SpringApplication.run(PlatformSystemApplication.class, args);
    }
}
