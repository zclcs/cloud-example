package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.RateLimitLog;
import com.zclcs.platform.system.api.entity.ao.RateLimitLogAo;
import com.zclcs.platform.system.api.entity.vo.RateLimitLogVo;
import com.zclcs.platform.system.biz.service.RateLimitLogService;
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
 * 限流拦截日志 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:53.040
 */
@Slf4j
@RestController
@RequestMapping("rateLimitLog")
@RequiredArgsConstructor
@Tag(name = "限流拦截日志")
public class RateLimitLogController {

    private final RateLimitLogService rateLimitLogService;

    @GetMapping
    @Operation(summary = "限流拦截日志查询（分页）")
    @PreAuthorize("hasAuthority('rateLimitLog:view')")
    public BaseRsp<BasePage<RateLimitLogVo>> findRateLimitLogPage(@Validated BasePageAo basePageAo, @Validated RateLimitLogVo rateLimitLogVo) {
        BasePage<RateLimitLogVo> page = this.rateLimitLogService.findRateLimitLogPage(basePageAo, rateLimitLogVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "限流拦截日志查询（集合）")
    @PreAuthorize("hasAuthority('rateLimitLog:view')")
    public BaseRsp<List<RateLimitLogVo>> findRateLimitLogList(@Validated RateLimitLogVo rateLimitLogVo) {
        List<RateLimitLogVo> list = this.rateLimitLogService.findRateLimitLogList(rateLimitLogVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "限流拦截日志查询（单个）")
    @PreAuthorize("hasAuthority('rateLimitLog:view')")
    public BaseRsp<RateLimitLogVo> findRateLimitLog(@Validated RateLimitLogVo rateLimitLogVo) {
        RateLimitLogVo rateLimitLog = this.rateLimitLogService.findRateLimitLog(rateLimitLogVo);
        return BaseRspUtil.data(rateLimitLog);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('rateLimitLog:add')")
    @ControllerEndpoint(operation = "新增限流拦截日志")
    @Operation(summary = "新增限流拦截日志")
    public BaseRsp<RateLimitLog> addRateLimitLog(@RequestBody @Validated RateLimitLogAo rateLimitLogAo) {
        return BaseRspUtil.data(this.rateLimitLogService.createRateLimitLog(rateLimitLogAo));
    }

    @DeleteMapping("/{rateLimitLogIds}")
    @PreAuthorize("hasAuthority('rateLimitLog:delete')")
    @ControllerEndpoint(operation = "删除限流拦截日志")
    @Operation(summary = "删除限流拦截日志")
    @Parameters({
            @Parameter(name = "rateLimitLogIds", description = "限流拦截日志id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRateLimitLog(@NotBlank(message = "{required}") @PathVariable String rateLimitLogIds) {
        List<Long> ids = Arrays.stream(rateLimitLogIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.rateLimitLogService.deleteRateLimitLog(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('rateLimitLog:update')")
    @ControllerEndpoint(operation = "修改限流拦截日志")
    @Operation(summary = "修改限流拦截日志")
    public BaseRsp<RateLimitLog> updateRateLimitLog(@RequestBody @Validated(UpdateStrategy.class) RateLimitLogAo rateLimitLogAo) {
        return BaseRspUtil.data(this.rateLimitLogService.updateRateLimitLog(rateLimitLogAo));
    }
}