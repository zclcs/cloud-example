package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.vo.LoginLogVo;
import com.zclcs.platform.system.service.LoginLogService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录日志
 *
 * @author zclcs
 * @date 2023-01-10 10:39:57.150
 */
@Slf4j
@RestController
@RequestMapping("/loginLog")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

    /**
     * 登录日志查询（分页）
     * 权限: loginLog:view
     *
     * @see LoginLogService#findLoginLogPage(BasePageAo, LoginLogVo)
     */
    @GetMapping
    @SaCheckPermission("loginLog:view")
    public BaseRsp<BasePage<LoginLogVo>> findLoginLogPage(@Validated BasePageAo basePageAo, @Validated LoginLogVo loginLogVo) {
        BasePage<LoginLogVo> page = this.loginLogService.findLoginLogPage(basePageAo, loginLogVo);
        return RspUtil.data(page);
    }

    /**
     * 登录日志查询（集合）
     * 权限: loginLog:view
     *
     * @see LoginLogService#findLoginLogList(LoginLogVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("loginLog:view")
    public BaseRsp<List<LoginLogVo>> findLoginLogList(@Validated LoginLogVo loginLogVo) {
        List<LoginLogVo> list = this.loginLogService.findLoginLogList(loginLogVo);
        return RspUtil.data(list);
    }

    /**
     * 登录日志查询（单个）
     * 权限: loginLog:view
     *
     * @see LoginLogService#findLoginLog(LoginLogVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("loginLog:view")
    public BaseRsp<LoginLogVo> findLoginLog(@Validated LoginLogVo loginLogVo) {
        LoginLogVo loginLog = this.loginLogService.findLoginLog(loginLogVo);
        return RspUtil.data(loginLog);
    }

    /**
     * 删除登录日志
     * 权限: loginLog:delete
     *
     * @see LoginLogService#deleteLoginLog(List)
     */
    @DeleteMapping("/{loginLogIds}")
    @SaCheckPermission("loginLog:delete")
    @ControllerEndpoint(operation = "删除登录日志")
    public BaseRsp<String> deleteLoginLog(@NotBlank(message = "{required}") @PathVariable String loginLogIds) {
        List<Long> ids = Arrays.stream(loginLogIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.loginLogService.deleteLoginLog(ids);
        return RspUtil.message("删除成功");
    }
}