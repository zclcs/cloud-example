package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.Log;
import com.zclcs.platform.system.api.entity.ao.LogAo;
import com.zclcs.platform.system.api.entity.vo.LogVo;
import com.zclcs.platform.system.biz.service.LogService;
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
 * 用户操作日志 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
 */
@Slf4j
@RestController
@RequestMapping("log")
@RequiredArgsConstructor
@Tag(name = "用户操作日志")
public class LogController {

    private final LogService logService;

    @GetMapping
    @Operation(summary = "用户操作日志查询（分页）")
    @PreAuthorize("hasAuthority('log:view')")
    public BaseRsp<BasePage<LogVo>> findLogPage(@Validated BasePageAo basePageAo, @Validated LogVo logVo) {
        BasePage<LogVo> page = this.logService.findLogPage(basePageAo, logVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "用户操作日志查询（集合）")
    @PreAuthorize("hasAuthority('log:view')")
    public BaseRsp<List<LogVo>> findLogList(@Validated LogVo logVo) {
        List<LogVo> list = this.logService.findLogList(logVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "用户操作日志查询（单个）")
    @PreAuthorize("hasAuthority('log:view')")
    public BaseRsp<LogVo> findLog(@Validated LogVo logVo) {
        LogVo log = this.logService.findLog(logVo);
        return BaseRspUtil.data(log);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('log:add')")
    @ControllerEndpoint(operation = "新增用户操作日志")
    @Operation(summary = "新增用户操作日志")
    public BaseRsp<Log> addLog(@RequestBody @Validated LogAo logAo) {
        return BaseRspUtil.data(this.logService.createLog(logAo));
    }

    @DeleteMapping("/{logIds}")
    @PreAuthorize("hasAuthority('log:delete')")
    @ControllerEndpoint(operation = "删除用户操作日志")
    @Operation(summary = "删除用户操作日志")
    @Parameters({
            @Parameter(name = "logIds", description = "用户操作日志id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteLog(@NotBlank(message = "{required}") @PathVariable String logIds) {
        List<Long> ids = Arrays.stream(logIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.logService.deleteLog(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('log:update')")
    @ControllerEndpoint(operation = "修改用户操作日志")
    @Operation(summary = "修改用户操作日志")
    public BaseRsp<Log> updateLog(@RequestBody @Validated(UpdateStrategy.class) LogAo logAo) {
        return BaseRspUtil.data(this.logService.updateLog(logAo));
    }
}