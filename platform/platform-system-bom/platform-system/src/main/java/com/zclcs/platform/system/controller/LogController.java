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
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户操作日志
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
 */
@Slf4j
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    /**
     * 用户操作日志查询（分页）
     * 权限: log:view 或者 user:detail:view
     *
     * @see LogService#findLogPage(BasePageAo, LogVo)
     */
    @GetMapping("/page")
    @SaCheckPermission(value = {"log:view", "user:detail:view"}, mode = SaMode.OR)
    public BaseRsp<BasePage<LogVo>> findLogPage(@Validated BasePageAo basePageAo, @Validated LogVo logVo) {
        BasePage<LogVo> page = this.logService.findLogPage(basePageAo, logVo);
        return RspUtil.data(page);
    }

    /**
     * 用户操作日志查询（集合）
     * 权限: log:view
     *
     * @see LogService#findLogList(LogVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("log:view")
    public BaseRsp<List<LogVo>> findLogList(@Validated LogVo logVo) {
        List<LogVo> list = this.logService.findLogList(logVo);
        return RspUtil.data(list);
    }

    /**
     * 用户操作日志查询（单个）
     * 权限: log:view
     *
     * @see LogService#findLog(LogVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("log:view")
    public BaseRsp<LogVo> findLog(@Validated LogVo logVo) {
        LogVo log = this.logService.findLog(logVo);
        return RspUtil.data(log);
    }

    /**
     * 保存日志
     * 权限: 仅限内部调用
     *
     * @see LogService#createLog(LogAo)
     */
    @PostMapping
    @Inner
    public void saveLog(@RequestBody @Validated LogAo logAo) {
        this.logService.createLog(logAo);
    }

    /**
     * 删除用户操作日志
     * 权限: log:delete
     *
     * @param logIds 用户操作日志id集合(,分隔)
     * @see LogService#deleteLog(List)
     */
    @DeleteMapping("/{logIds}")
    @SaCheckPermission("log:delete")
    @ControllerEndpoint(operation = "删除用户操作日志")
    public BaseRsp<String> deleteLog(@NotBlank(message = "{required}") @PathVariable String logIds) {
        List<Long> ids = Arrays.stream(logIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.logService.deleteLog(ids);
        return RspUtil.message("删除成功");
    }
}