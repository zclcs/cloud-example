package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.platform.system.api.entity.vo.LoginLogVo;
import com.zclcs.platform.system.biz.service.LoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录日志 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:57.150
 */
@Slf4j
@RestController
@RequestMapping("/loginLog")
@RequiredArgsConstructor
@Tag(name = "登录日志")
public class LoginLogController {

    private final LoginLogService loginLogService;

    @GetMapping
    @PreAuthorize("hasAuthority('loginLog:view')")
    @Operation(summary = "登录日志查询（分页）")
    public BaseRsp<BasePage<LoginLogVo>> findLoginLogPage(@Validated BasePageAo basePageAo, @Validated LoginLogVo loginLogVo) {
        BasePage<LoginLogVo> page = this.loginLogService.findLoginLogPage(basePageAo, loginLogVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('loginLog:view')")
    @Operation(summary = "登录日志查询（集合）")
    public BaseRsp<List<LoginLogVo>> findLoginLogList(@Validated LoginLogVo loginLogVo) {
        List<LoginLogVo> list = this.loginLogService.findLoginLogList(loginLogVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @PreAuthorize("hasAuthority('loginLog:view')")
    @Operation(summary = "登录日志查询（单个）")
    public BaseRsp<LoginLogVo> findLoginLog(@Validated LoginLogVo loginLogVo) {
        LoginLogVo loginLog = this.loginLogService.findLoginLog(loginLogVo);
        return RspUtil.data(loginLog);
    }

    @DeleteMapping("/{loginLogIds}")
    @PreAuthorize("hasAuthority('loginLog:delete')")
    @ControllerEndpoint(operation = "删除登录日志")
    @Operation(summary = "删除登录日志")
    @Parameters({
            @Parameter(name = "loginLogIds", description = "登录日志id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteLoginLog(@NotBlank(message = "{required}") @PathVariable String loginLogIds) {
        List<Long> ids = Arrays.stream(loginLogIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.loginLogService.deleteLoginLog(ids);
        return RspUtil.message("删除成功");
    }
}