package com.zclcs.platform.system.listener;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.constant.RabbitMq;
import com.zclcs.cloud.lib.rabbit.mq.properties.MyRabbitMqProperties;
import com.zclcs.cloud.lib.rabbit.mq.service.RabbitService;
import com.zclcs.common.web.starter.utils.WebUtil;
import com.zclcs.platform.system.api.bean.ao.LoginLogAo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 用户行为监听
 *
 * @author zclcs
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSaTokenListener implements SaTokenListener {

    private final RabbitService rabbitService;
    private final MyRabbitMqProperties myRabbitMqProperties;

    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        LoginLogAo loginLog = getLoginLog((String) loginId);
        loginLog.setLoginType(Dict.LOGIN_LOG_LOGIN_TYPE_01);
        rabbitService.convertAndSend(myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG), loginLog);
    }

    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        Object object = Optional.ofNullable(loginId).orElse("system");
        LoginLogAo loginLog = getLoginLog((String) object);
        loginLog.setLoginType(Dict.LOGIN_LOG_LOGIN_TYPE_03);
        rabbitService.convertAndSend(myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG), loginLog);
    }

    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {

    }

    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {

    }

    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {

    }

    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {

    }

    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {

    }

    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {

    }

    @Override
    public void doCreateSession(String id) {

    }

    @Override
    public void doLogoutSession(String id) {

    }

    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {

    }

    private LoginLogAo getLoginLog(String username) {
        HttpServletRequest httpServletRequest = WebUtil.getHttpServletRequest();
        LoginLogAo loginLog = new LoginLogAo();
        loginLog.setUsername(username);
        loginLog.setLoginTime(LocalDateTime.now());
        String httpServletRequestIpAddress = WebUtil.getHttpServletRequestIpAddress();
        loginLog.setIp(httpServletRequestIpAddress);
        String userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);
        UserAgent ua = UserAgentUtil.parse(userAgent);
        loginLog.setSystem(ua.getOs().getName());
        loginLog.setBrowser(ua.getBrowser().getName());
        loginLog.setUsername(username);
        // 发送异步日志事件
        return loginLog;
    }
}
