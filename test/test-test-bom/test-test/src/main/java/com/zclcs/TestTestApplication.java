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
@MapperScan("com.zclcs.test.test.mapper")
public class TestTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTestApplication.class, args);
    }
}
