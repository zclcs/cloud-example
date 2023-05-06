package com.zclcs.platform.auth.support.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.constant.RabbitMq;
import com.zclcs.cloud.lib.core.enums.ExchangeType;
import com.zclcs.cloud.lib.rabbit.mq.entity.MessageStruct;
import com.zclcs.cloud.lib.rabbit.mq.properties.MyRabbitMqProperties;
import com.zclcs.cloud.lib.rabbit.mq.utils.RabbitKeyUtil;
import com.zclcs.cloud.lib.security.utils.LoginLogUtil;
import com.zclcs.platform.system.api.entity.ao.LoginLogAo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 * <p>
 * 事件机制处理退出相关
 */
@Slf4j
@Component
public class MyLogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

    private ObjectMapper objectMapper;
    private RabbitTemplate rabbitTemplate;
    private MyRabbitMqProperties myRabbitMqProperties;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setMyRabbitMqProperties(MyRabbitMqProperties myRabbitMqProperties) {
        this.myRabbitMqProperties = myRabbitMqProperties;
    }

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            handle(authentication);
        }
    }

    /**
     * 处理退出成功方法
     * <p>
     * 获取到登录的authentication 对象
     *
     * @param authentication 登录对象
     */
    @SneakyThrows(value = JsonProcessingException.class)
    public void handle(Authentication authentication) {
        log.info("用户：{} 退出成功", authentication.getPrincipal());
        LoginLogAo loginLog = LoginLogUtil.getLoginLog();
        loginLog.setLoginType(Dict.LOGIN_LOG_LOGIN_TYPE_03);
        loginLog.setUsername(authentication.getName());
        // 发送异步日志事件
        loginLog.setCreateBy(authentication.getName());
        loginLog.setUpdateBy(authentication.getName());
        rabbitTemplate.convertAndSend(RabbitKeyUtil.getExchangeName(
                        myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG).getQueueName(), ExchangeType.DIRECT), RabbitKeyUtil.getRouteKey(
                        myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG).getQueueName()),
                MessageStruct.builder().message(objectMapper.writeValueAsString(loginLog)).build());
    }

}
