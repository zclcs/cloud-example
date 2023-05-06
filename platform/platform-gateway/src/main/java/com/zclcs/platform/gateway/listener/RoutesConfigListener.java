package com.zclcs.platform.gateway.listener;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.cloud.lib.core.utils.SpringContextHolderUtil;
import com.zclcs.platform.gateway.handler.RoutesHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public void setNacosProperties(MyNacosProperties myNacosProperties) {
        this.myNacosProperties = myNacosProperties;
    }

    @Autowired
    public void setRoutesHandler(RoutesHandler routesHandler) {
        this.routesHandler = routesHandler;
    }

    @PostConstruct
    public void dynamicRouteByNacosListener() throws NacosException {
        String serverAddr = myNacosProperties.getServerAddr();
        String namespace = myNacosProperties.getNamespace();
        String group = myNacosProperties.getGroup();
        String username = myNacosProperties.getUsername();
        String password = myNacosProperties.getPassword();
        String dataId = "gateway-routes.json";
        log.info("gateway-routes dynamicRouteByNacosListener config serverAddr is {} namespace is {} group is {} username is {} password is {}",
                serverAddr, namespace, group, username, password);
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        properties.put(PropertyKeyConst.USERNAME, username);
        properties.put(PropertyKeyConst.PASSWORD, password);
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
                return SpringContextHolderUtil.getBean(CommonCore.ASYNC_POOL);
            }
        });

        // 获取当前的配置
        String initConfig = configService.getConfig(dataId, group, 5000);

        // 立即更新
        routesHandler.refreshAll(initConfig);
    }

}
