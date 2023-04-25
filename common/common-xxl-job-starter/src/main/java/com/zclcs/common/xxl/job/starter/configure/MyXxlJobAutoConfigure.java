package com.zclcs.common.xxl.job.starter.configure;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.zclcs.common.xxl.job.starter.properties.MyXxlJobProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@AutoConfiguration
@EnableConfigurationProperties(MyXxlJobProperties.class)
@ConditionalOnProperty(value = "my.xxl.job.enable", havingValue = "true", matchIfMissing = true)
public class MyXxlJobAutoConfigure {

    private final MyXxlJobProperties properties;

    public MyXxlJobAutoConfigure(MyXxlJobProperties properties) {
        this.properties = properties;
    }

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(properties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(properties.getAppName());
        xxlJobSpringExecutor.setAddress(properties.getAddress());
        xxlJobSpringExecutor.setIp(properties.getIp());
        xxlJobSpringExecutor.setPort(properties.getPort());
        xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(properties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(properties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }

}