package com.zclcs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zclcs
 * 配置同步服务
 */
@SpringBootApplication
public class ThirdPartConfigSyncApplication {

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        SpringApplication.run(ThirdPartConfigSyncApplication.class, args);
    }
}
