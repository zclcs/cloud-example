package com.zclcs.platform.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.constant.RabbitMq;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.rabbit.mq.properties.MyRabbitMqProperties;
import com.zclcs.cloud.lib.rabbit.mq.service.RabbitService;
import com.zclcs.cloud.lib.sa.token.api.utils.LoginHelper;
import com.zclcs.common.web.starter.utils.WebUtil;
import com.zclcs.platform.system.api.bean.ao.*;
import com.zclcs.platform.system.api.bean.vo.UserTokenVo;
import com.zclcs.platform.system.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 登录
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Slf4j
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final RabbitService rabbitService;
    private final MyRabbitMqProperties myRabbitMqProperties;

    /**
     * 通过用户名获取token
     * 接口调用需加上验证码、password需加密，如何加密见参数说明
     *
     * @param loginByUsernameAo {@link LoginByUsernameAo}
     * @return {@link UserTokenVo}
     */
    @PostMapping("/login/token/byUsername")
    public BaseRsp<UserTokenVo> tokenByUsername(@Validated ValidateCodeAo validateCodeAo, @RequestBody @Validated LoginByUsernameAo loginByUsernameAo) {
        UserTokenVo userTokenVo = loginService.loginByUsername(loginByUsernameAo);
        LoginLogAo loginLog = getLoginLog(userTokenVo.getUserinfo().getUsername());
        loginLog.setLoginType(Dict.LOGIN_LOG_LOGIN_TYPE_01);
        rabbitService.convertAndSend(myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG), loginLog);
        return RspUtil.data(userTokenVo);
    }

    /**
     * 通过手机号获取token
     *
     * @param loginByMobileAo {@link LoginByMobileAo}
     * @return {@link UserTokenVo}
     */
    @PostMapping("/login/token/byMobile")
    public BaseRsp<UserTokenVo> tokenByMobile(@Validated ValidateMobileAo validateMobileAo, @RequestBody @Validated LoginByMobileAo loginByMobileAo) {
        UserTokenVo userTokenVo = loginService.loginByMobile(loginByMobileAo.getMobile());
        LoginLogAo loginLog = getLoginLog(userTokenVo.getUserinfo().getUsername());
        loginLog.setLoginType(Dict.LOGIN_LOG_LOGIN_TYPE_01);
        rabbitService.convertAndSend(myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG), loginLog);
        return RspUtil.data(userTokenVo);
    }

    /**
     * 登出方法
     */
    @DeleteMapping("/logout")
    public BaseRsp<Object> logout() {
        String username = "system";
        try {
            username = LoginHelper.getUsername();
            StpUtil.logout();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        LoginLogAo loginLog = getLoginLog(username);
        loginLog.setLoginType(Dict.LOGIN_LOG_LOGIN_TYPE_03);
        rabbitService.convertAndSend(myRabbitMqProperties.getDirectQueues().get(RabbitMq.SYSTEM_LOGIN_LOG), loginLog);
        return RspUtil.message();
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
        loginLog.setCreateBy(username);
        loginLog.setUpdateBy(username);
        return loginLog;
    }
}