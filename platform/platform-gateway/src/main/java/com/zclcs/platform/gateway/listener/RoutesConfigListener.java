package com.zclcs.platform.gateway.listener;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.utils.SpringContextHolderUtil;
import com.zclcs.platform.gateway.handler.RoutesHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author zclcs
 */
@Slf4j
@Component
public class RoutesConfigListener {

    private static final List<String> ROUTE_LIST = new ArrayList<>();
    private final String dataId = "gateway-routes.json";

    private RoutesHandler routesHandler;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Value("${spring.cloud.nacos.config.group}")
    private String group;

    @Autowired
    public void setRoutesHandler(RoutesHandler routesHandler) {
        this.routesHandler = routesHandler;
    }

    @PostConstruct
    public void dynamicRouteByNacosListener() throws NacosException {
        log.info("gateway-routes dynamicRouteByNacosListener config serverAddr is {} namespace is {} group is {}", serverAddr, namespace, group);
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
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
                return SpringContextHolderUtil.getBean(MyConstant.ASYNC_POOL);
            }
        });

        // 获取当前的配置
        String initConfig = configService.getConfig(dataId, group, 5000);

        // 立即更新
        routesHandler.refreshAll(initConfig);
    }

}
