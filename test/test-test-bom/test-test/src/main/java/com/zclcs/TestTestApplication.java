package com.zclcs;

import com.zclcs.common.doc.starter.annotation.EnableMyDoc;
import com.zclcs.common.feign.annotation.EnableMyFeignClients;
import com.zclcs.common.security.annotation.EnableMyResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhouc
 */
@EnableAsync
@EnableMyResourceServer
@EnableMyFeignClients
@EnableDiscoveryClient
@MapperScan("com.zclcs.test.test.mapper")
@SpringBootApplication
@EnableMyDoc
public class TestTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTestApplication.class, args);
    }
}
