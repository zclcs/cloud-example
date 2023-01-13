package com.zclcs;

import com.zclcs.common.feign.starter.annotation.EnableMyFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhouc
 */
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.zclcs.platform.system.biz.mapper")
@EnableDiscoveryClient
@EnableMyFeignClients
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
