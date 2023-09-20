package com.zclcs.platform.gateway.listener;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.platform.gateway.handler.RoutesHandler;
import jakarta.annotation.PostConstruct;
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
public class RoutesConfigListener {

    private RoutesHandler routesHandler;

    private MyNacosProperties myNacosProperties;

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public void setNacosProperties(MyNacosProperties myNacosProperties) {
        this.myNacosProperties = myNacosProperties;
    }

    @Autowired
    public void setRoutesHandler(RoutesHandler routesHandler) {
        this.routesHandler = routesHandler;
    }

    @Qualifier(CommonCore.ASYNC_POOL)
    @Autowired
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @PostConstruct
    public void dynamicRouteByNacosListener() throws NacosException {
        String dataId = "gateway-routes.json";
        String group = myNacosProperties.getGroup();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, myNacosProperties.getServerAddr());
        properties.put(PropertyKeyConst.NAMESPACE, myNacosProperties.getNamespace());
        properties.put(PropertyKeyConst.USERNAME, myNacosProperties.getUsername());
        properties.put(PropertyKeyConst.PASSWORD, myNacosProperties.getPassword());
        log.debug("dynamicRouteByNacosListener nacos {}", properties);
        ConfigService configService = NacosFactory.createConfigService(properties);
        // 添加监听，nacos上的配置变更后会执行
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                // 解析和处理都交给RouteOperator完成
                routesHandler.refreshAll(configInfo);
            }

            @Override
            public Executor getExecutor() {
                return threadPoolTaskExecutor;
            }
        });

        // 获取当前的配置
        String initConfig = configService.getConfig(dataId, group, 5000);

        // 立即更新
        routesHandler.refreshAll(initConfig);
    }

}
