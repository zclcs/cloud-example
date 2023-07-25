package com.zclcs.platform.system.controller;

import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.LoginByMobileAo;
import com.zclcs.platform.system.api.bean.ao.LoginByUsernameAo;
import com.zclcs.platform.system.api.bean.vo.UserTokenVo;
import com.zclcs.platform.system.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name = "登录")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/token/byUsername")
    @Operation(summary = "通过用户名获取token")
    public BaseRsp<UserTokenVo> tokenByUsername(@Validated LoginByUsernameAo loginByUsernameAo) {
        UserTokenVo userTokenVo = loginService.loginByUsername(loginByUsernameAo);
        return RspUtil.data(userTokenVo);
    }

    @PostMapping("/token/byMobile")
    @Operation(summary = "通过手机号获取token")
    public BaseRsp<UserTokenVo> tokenByMobile(@Validated LoginByMobileAo loginByMobileAo) {
        UserTokenVo userTokenVo = loginService.loginByMobile(loginByMobileAo.getMobile());
        return RspUtil.data(userTokenVo);
    }
}