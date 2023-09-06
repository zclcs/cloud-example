package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.RouteLogAo;
import com.zclcs.platform.system.api.bean.entity.RouteLog;
import com.zclcs.platform.system.api.bean.vo.RouteLogVo;
import com.zclcs.platform.system.service.RouteLogService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 网关转发日志
 *
 * @author zclcs
 * @since 2023-01-10 10:40:09.958
 */
@Slf4j
@RestController
@RequestMapping("/routeLog")
@RequiredArgsConstructor
public class RouteLogController {

    private final RouteLogService routeLogService;

    /**
     * 网关转发日志查询（分页）
     * 权限: routeLog:view
     *
     * @see RouteLogService#findRouteLogPage(BasePageAo, RouteLogVo)
     */
    @GetMapping
    @SaCheckPermission("routeLog:view")
    public BaseRsp<BasePage<RouteLogVo>> findRouteLogPage(@Validated BasePageAo basePageAo, @Validated RouteLogVo routeLogVo) {
        BasePage<RouteLogVo> page = this.routeLogService.findRouteLogPage(basePageAo, routeLogVo);
        return RspUtil.data(page);
    }

    /**
     * 网关转发日志查询（集合）
     * 权限: routeLog:view
     *
     * @see RouteLogService#findRouteLogList(RouteLogVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("routeLog:view")
    public BaseRsp<List<RouteLogVo>> findRouteLogList(@Validated RouteLogVo routeLogVo) {
        List<RouteLogVo> list = this.routeLogService.findRouteLogList(routeLogVo);
        return RspUtil.data(list);
    }

    /**
     * 网关转发日志查询（单个）
     * 权限: routeLog:view
     *
     * @see RouteLogService#findRouteLog(RouteLogVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("routeLog:view")
    public BaseRsp<RouteLogVo> findRouteLog(@Validated RouteLogVo routeLogVo) {
        RouteLogVo routeLog = this.routeLogService.findRouteLog(routeLogVo);
        return RspUtil.data(routeLog);
    }

    /**
     * 新增网关转发日志
     * 权限: routeLog:add
     *
     * @see RouteLogService#createRouteLog(RouteLogAo)
     */
    @PostMapping
    @SaCheckPermission("routeLog:add")
    @ControllerEndpoint(operation = "新增网关转发日志")
    public BaseRsp<RouteLog> addRouteLog(@RequestBody @Validated RouteLogAo routeLogAo) {
        return RspUtil.data(this.routeLogService.createRouteLog(routeLogAo));
    }

    /**
     * 删除网关转发日志
     * 权限: routeLog:delete
     *
     * @param routeLogIds 网关转发日志id集合(,分隔)
     * @see RouteLogService#deleteRouteLog(List)
     */
    @DeleteMapping("/{routeLogIds}")
    @SaCheckPermission("routeLog:delete")
    @ControllerEndpoint(operation = "删除网关转发日志")
    public BaseRsp<String> deleteRouteLog(@NotBlank(message = "{required}") @PathVariable String routeLogIds) {
        List<Long> ids = Arrays.stream(routeLogIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.routeLogService.deleteRouteLog(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 导出网关转发日志
     * 权限: routeLog:export
     *
     * @see RouteLogService#exportExcel(RouteLogVo)
     */
    @GetMapping("/export/excel")
    @SaCheckPermission("routeLog:export")
    public void exportRouteLogList(@Validated RouteLogVo routeLogVo) {
        routeLogService.exportExcel(routeLogVo);
    }

    /**
     * 导入网关转发日志
     * 权限: routeLog:import
     *
     * @see RouteLogService#importExcel(MultipartFile)
     */
    @GetMapping("/import/excel")
    @SaCheckPermission("routeLog:import")
    public BaseRsp<String> importRouteLogList(MultipartFile file) {
        routeLogService.importExcel(file);
        return RspUtil.message("导入成功");
    }
}