package com.zclcs.cloud.lib.logging.listener;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.cloud.lib.logging.handler.LoggingLevelHandler;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author zclcs
 */
@Slf4j
@Component
public class LoggingConfigListener {

    private LoggingLevelHandler loggingLevelHandler;

    private MyNacosProperties myNacosProperties;

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public void setNacosProperties(MyNacosProperties myNacosProperties) {
        this.myNacosProperties = myNacosProperties;
    }

    @Autowired
    public void setLoggingLevelHandler(LoggingLevelHandler loggingLevelHandler) {
        this.loggingLevelHandler = loggingLevelHandler;
    }

    @Qualifier(CommonCore.NACOS_CONFIG)
    @Autowired
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @PostConstruct
    public void dynamicLoggingLevelListener() throws NacosException, JsonProcessingException {
        String dataId = "logging.json";
        String group = myNacosProperties.getGroup();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, myNacosProperties.getServerAddr());
        properties.put(PropertyKeyConst.NAMESPACE, myNacosProperties.getNamespace());
        properties.put(PropertyKeyConst.USERNAME, myNacosProperties.getUsername());
        properties.put(PropertyKeyConst.PASSWORD, myNacosProperties.getPassword());
        log.debug("dynamicLoggingLevelListener nacos {}", properties);
        ConfigService configService = NacosFactory.createConfigService(properties);
        // 添加监听，nacos上的配置变更后会执行
        configService.addListener(dataId, group, new Listener() {
            @SneakyThrows
            @Override
            public void receiveConfigInfo(String configInfo) {
                loggingLevelHandler.configLoggingLevel(configInfo);
            }

            @Override
            public Executor getExecutor() {
                return threadPoolTaskExecutor;
            }
        });

        // 获取当前的配置
        String initConfig = configService.getConfig(dataId, group, 5000);

        // 立即更新
        loggingLevelHandler.configLoggingLevel(initConfig);
    }

}
