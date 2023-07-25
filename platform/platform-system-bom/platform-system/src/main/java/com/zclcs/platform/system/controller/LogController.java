package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.aop.ao.LogAo;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.cloud.lib.security.lite.annotation.Inner;
import com.zclcs.platform.system.api.bean.vo.LogVo;
import com.zclcs.platform.system.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户操作日志 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
 */
@Slf4j
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
@Tag(name = "用户操作日志")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class LogController {

    private final LogService logService;

    @GetMapping("/page")
    @SaCheckPermission(value = {"log:view", "user:detail:view"}, mode = SaMode.OR)
    @Operation(summary = "用户操作日志查询（分页）")
    public BaseRsp<BasePage<LogVo>> findLogPage(@Validated BasePageAo basePageAo, @Validated LogVo logVo) {
        BasePage<LogVo> page = this.logService.findLogPage(basePageAo, logVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @SaCheckPermission("log:view")
    @Operation(summary = "用户操作日志查询（集合）")
    public BaseRsp<List<LogVo>> findLogList(@Validated LogVo logVo) {
        List<LogVo> list = this.logService.findLogList(logVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @SaCheckPermission("log:view")
    @Operation(summary = "用户操作日志查询（单个）")
    public BaseRsp<LogVo> findLog(@Validated LogVo logVo) {
        LogVo log = this.logService.findLog(logVo);
        return RspUtil.data(log);
    }

    @PostMapping
    @Operation(summary = "保存日志")
    @Inner
    public void saveLog(@RequestBody @Validated LogAo logAo) {
        this.logService.createLog(logAo);
    }

    @DeleteMapping("/{logIds}")
    @SaCheckPermission("log:delete")
    @ControllerEndpoint(operation = "删除用户操作日志")
    @Operation(summary = "删除用户操作日志")
    @Parameters({
            @Parameter(name = "logIds", description = "用户操作日志id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteLog(@NotBlank(message = "{required}") @PathVariable String logIds) {
        List<Long> ids = Arrays.stream(logIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.logService.deleteLog(ids);
        return RspUtil.message("删除成功");
    }
}