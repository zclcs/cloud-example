package com.zclcs.common.xxl.job.starter.configure;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.zclcs.common.xxl.job.starter.properties.MyXxlJobProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * xxl-job config
 *
 * @author zclcs
 */
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "my.xxl.job.enable", havingValue = "true", matchIfMissing = true)
public class MyXxlJobAutoConfigure {

    private final MyXxlJobProperties myXxlJobProperties;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(myXxlJobProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(myXxlJobProperties.getAppName());
        xxlJobSpringExecutor.setAddress(myXxlJobProperties.getAddress());
        xxlJobSpringExecutor.setIp(myXxlJobProperties.getIp());
        xxlJobSpringExecutor.setPort(myXxlJobProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(myXxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(myXxlJobProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(myXxlJobProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }

}