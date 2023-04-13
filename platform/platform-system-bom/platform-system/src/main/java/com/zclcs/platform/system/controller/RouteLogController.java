package com.zclcs.platform.system.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.platform.system.api.entity.RouteLog;
import com.zclcs.platform.system.api.entity.ao.RouteLogAo;
import com.zclcs.platform.system.api.entity.vo.RouteLogVo;
import com.zclcs.platform.system.service.RouteLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 网关转发日志 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:40:09.958
 */
@Slf4j
@RestController
@RequestMapping("/routeLog")
@RequiredArgsConstructor
@Tag(name = "网关转发日志")
public class RouteLogController {

    private final RouteLogService routeLogService;

    @GetMapping
    @PreAuthorize("hasAuthority('routeLog:view')")
    @Operation(summary = "网关转发日志查询（分页）")
    public BaseRsp<BasePage<RouteLogVo>> findRouteLogPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated RouteLogVo routeLogVo) {
        BasePage<RouteLogVo> page = this.routeLogService.findRouteLogPage(basePageAo, routeLogVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('routeLog:view')")
    @Operation(summary = "网关转发日志查询（集合）")
    public BaseRsp<List<RouteLogVo>> findRouteLogList(@ParameterObject @Validated RouteLogVo routeLogVo) {
        List<RouteLogVo> list = this.routeLogService.findRouteLogList(routeLogVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @PreAuthorize("hasAuthority('routeLog:view')")
    @Operation(summary = "网关转发日志查询（单个）")
    public BaseRsp<RouteLogVo> findRouteLog(@ParameterObject @Validated RouteLogVo routeLogVo) {
        RouteLogVo routeLog = this.routeLogService.findRouteLog(routeLogVo);
        return RspUtil.data(routeLog);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('routeLog:add')")
    @ControllerEndpoint(operation = "新增网关转发日志")
    @Operation(summary = "新增网关转发日志")
    public BaseRsp<RouteLog> addRouteLog(@RequestBody @Validated RouteLogAo routeLogAo) {
        return RspUtil.data(this.routeLogService.createRouteLog(routeLogAo));
    }

    @DeleteMapping("/{routeLogIds}")
    @PreAuthorize("hasAuthority('routeLog:delete')")
    @ControllerEndpoint(operation = "删除网关转发日志")
    @Operation(summary = "删除网关转发日志")
    @Parameters({
            @Parameter(name = "routeLogIds", description = "网关转发日志id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRouteLog(@NotBlank(message = "{required}") @PathVariable String routeLogIds) {
        List<Long> ids = Arrays.stream(routeLogIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.routeLogService.deleteRouteLog(ids);
        return RspUtil.message("删除成功");
    }
}