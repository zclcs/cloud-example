package com.zclcs.platform.gateway.listener;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.properties.MyNacosProperties;
import com.zclcs.cloud.lib.core.utils.SpringContextHolderUtil;
import com.zclcs.platform.gateway.handler.FlowsHandler;
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
public class FlowsConfigListener {

    private FlowsHandler flowsHandler;

    private MyNacosProperties myNacosProperties;

    @Autowired
    public void setNacosProperties(MyNacosProperties myNacosProperties) {
        this.myNacosProperties = myNacosProperties;
    }

    @Autowired
    public void setFlowsHandler(FlowsHandler flowsHandler) {
        this.flowsHandler = flowsHandler;
    }

    @PostConstruct
    public void dynamicFlowByNacosListener() throws NacosException {
        String dataId = "gateway-flow.json";
        String group = myNacosProperties.getGroup();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, myNacosProperties.getServerAddr());
        properties.put(PropertyKeyConst.NAMESPACE, myNacosProperties.getNamespace());
        properties.put(PropertyKeyConst.USERNAME, myNacosProperties.getUsername());
        properties.put(PropertyKeyConst.PASSWORD, myNacosProperties.getPassword());
        log.debug("dynamicFlowByNacosListener nacos {}", properties);
        ConfigService configService = NacosFactory.createConfigService(properties);
        // 添加监听，nacos上的配置变更后会执行
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                flowsHandler.refreshAll(configInfo);
            }

            @Override
            public Executor getExecutor() {
                return SpringContextHolderUtil.getBean(CommonCore.ASYNC_POOL);
            }
        });

        // 获取当前的配置
        String initConfig = configService.getConfig(dataId, group, 5000);

        // 立即更新
        flowsHandler.refreshAll(initConfig);
    }

}