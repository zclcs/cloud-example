package com.zclcs.platform.system.controller;

import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.LoginByMobileAo;
import com.zclcs.platform.system.api.bean.ao.LoginByUsernameAo;
import com.zclcs.platform.system.api.bean.vo.UserTokenVo;
import com.zclcs.platform.system.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 通过用户名获取token
     * 接口调用需加上验证码、password需加密，如何加密见参数说明
     *
     * @param loginByUsernameAo {@link LoginByUsernameAo}
     * @return {@link UserTokenVo}
     */
    @PostMapping("/token/byUsername")
    public BaseRsp<UserTokenVo> tokenByUsername(@Validated LoginByUsernameAo loginByUsernameAo) {
        UserTokenVo userTokenVo = loginService.loginByUsername(loginByUsernameAo);
        return RspUtil.data(userTokenVo);
    }

    /**
     * 通过手机号获取token
     *
     * @param loginByMobileAo {@link LoginByMobileAo}
     * @return {@link UserTokenVo}
     */
    @PostMapping("/token/byMobile")
    public BaseRsp<UserTokenVo> tokenByMobile(@Validated LoginByMobileAo loginByMobileAo) {
        UserTokenVo userTokenVo = loginService.loginByMobile(loginByMobileAo.getMobile());
        return RspUtil.data(userTokenVo);
    }
}