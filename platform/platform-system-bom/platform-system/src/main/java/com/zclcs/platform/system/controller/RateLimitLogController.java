package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.RateLimitLogAo;
import com.zclcs.platform.system.api.bean.entity.RateLimitLog;
import com.zclcs.platform.system.api.bean.vo.RateLimitLogVo;
import com.zclcs.platform.system.service.RateLimitLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 限流拦截日志 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:53.040
 */
@Slf4j
@RestController
@RequestMapping("/rateLimitLog")
@RequiredArgsConstructor
@Tag(name = "限流拦截日志")
public class RateLimitLogController {

    private final RateLimitLogService rateLimitLogService;

    @GetMapping
    @SaCheckPermission("rateLimitLog:view")
    @Operation(summary = "限流拦截日志查询（分页）")
    public BaseRsp<BasePage<RateLimitLogVo>> findRateLimitLogPage(@Validated BasePageAo basePageAo, @Validated RateLimitLogVo rateLimitLogVo) {
        BasePage<RateLimitLogVo> page = this.rateLimitLogService.findRateLimitLogPage(basePageAo, rateLimitLogVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @SaCheckPermission("rateLimitLog:view")
    @Operation(summary = "限流拦截日志查询（集合）")
    public BaseRsp<List<RateLimitLogVo>> findRateLimitLogList(@Validated RateLimitLogVo rateLimitLogVo) {
        List<RateLimitLogVo> list = this.rateLimitLogService.findRateLimitLogList(rateLimitLogVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @SaCheckPermission("rateLimitLog:view")
    @Operation(summary = "限流拦截日志查询（单个）")
    public BaseRsp<RateLimitLogVo> findRateLimitLog(@Validated RateLimitLogVo rateLimitLogVo) {
        RateLimitLogVo rateLimitLog = this.rateLimitLogService.findRateLimitLog(rateLimitLogVo);
        return RspUtil.data(rateLimitLog);
    }

    @PostMapping
    @SaCheckPermission("rateLimitLog:add")
    @ControllerEndpoint(operation = "新增限流拦截日志")
    @Operation(summary = "新增限流拦截日志")
    public BaseRsp<RateLimitLog> addRateLimitLog(@RequestBody @Validated RateLimitLogAo rateLimitLogAo) {
        return RspUtil.data(this.rateLimitLogService.createRateLimitLog(rateLimitLogAo));
    }

    @DeleteMapping("/{rateLimitLogIds}")
    @SaCheckPermission("rateLimitLog:delete")
    @ControllerEndpoint(operation = "删除限流拦截日志")
    @Operation(summary = "删除限流拦截日志")
    @Parameters({
            @Parameter(name = "rateLimitLogIds", description = "限流拦截日志id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRateLimitLog(@NotBlank(message = "{required}") @PathVariable String rateLimitLogIds) {
        List<Long> ids = Arrays.stream(rateLimitLogIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.rateLimitLogService.deleteRateLimitLog(ids);
        return RspUtil.message("删除成功");
    }
}