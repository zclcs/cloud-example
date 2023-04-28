package com.zclcs.platform.system.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.Strings;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.aop.annotation.ControllerEndpoint;
import com.zclcs.platform.system.api.entity.BlockLog;
import com.zclcs.platform.system.api.entity.ao.BlockLogAo;
import com.zclcs.platform.system.api.entity.vo.BlockLogVo;
import com.zclcs.platform.system.service.BlockLogService;
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
 * 黑名单拦截日志 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:40:05.798
 */
@Slf4j
@RestController
@RequestMapping("/blockLog")
@RequiredArgsConstructor
@Tag(name = "黑名单拦截日志")
public class BlockLogController {

    private final BlockLogService blockLogService;

    @GetMapping
    @PreAuthorize("hasAuthority('blockLog:view')")
    @Operation(summary = "黑名单拦截日志查询（分页）")
    public BaseRsp<BasePage<BlockLogVo>> findBlockLogPage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated BlockLogVo blockLogVo) {
        BasePage<BlockLogVo> page = this.blockLogService.findBlockLogPage(basePageAo, blockLogVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('blockLog:view')")
    @Operation(summary = "黑名单拦截日志查询（集合）")
    public BaseRsp<List<BlockLogVo>> findBlockLogList(@ParameterObject @Validated BlockLogVo blockLogVo) {
        List<BlockLogVo> list = this.blockLogService.findBlockLogList(blockLogVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @PreAuthorize("hasAuthority('blockLog:view')")
    @Operation(summary = "黑名单拦截日志查询（单个）")
    public BaseRsp<BlockLogVo> findBlockLog(@ParameterObject @Validated BlockLogVo blockLogVo) {
        BlockLogVo blockLog = this.blockLogService.findBlockLog(blockLogVo);
        return RspUtil.data(blockLog);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('blockLog:add')")
    @ControllerEndpoint(operation = "新增黑名单拦截日志")
    @Operation(summary = "新增黑名单拦截日志")
    public BaseRsp<BlockLog> addBlockLog(@RequestBody @Validated BlockLogAo blockLogAo) {
        return RspUtil.data(this.blockLogService.createBlockLog(blockLogAo));
    }

    @DeleteMapping("/{blockLogIds}")
    @PreAuthorize("hasAuthority('blockLog:delete')")
    @ControllerEndpoint(operation = "删除黑名单拦截日志")
    @Operation(summary = "删除黑名单拦截日志")
    @Parameters({
            @Parameter(name = "blockLogIds", description = "黑名单拦截日志id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteBlockLog(@NotBlank(message = "{required}") @PathVariable String blockLogIds) {
        List<Long> ids = Arrays.stream(blockLogIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.blockLogService.deleteBlockLog(ids);
        return RspUtil.message("删除成功");
    }
}