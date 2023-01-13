package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.aop.starter.annotation.ControllerEndpoint;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.BaseRspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.BlockLog;
import com.zclcs.platform.system.api.entity.ao.BlockLogAo;
import com.zclcs.platform.system.api.entity.vo.BlockLogVo;
import com.zclcs.platform.system.biz.service.BlockLogService;
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
 * 黑名单拦截日志 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:40:05.798
 */
@Slf4j
@RestController
@RequestMapping("blockLog")
@RequiredArgsConstructor
@Tag(name = "黑名单拦截日志")
public class BlockLogController {

    private final BlockLogService blockLogService;

    @GetMapping
    @Operation(summary = "黑名单拦截日志查询（分页）")
    @PreAuthorize("hasAuthority('blockLog:view')")
    public BaseRsp<BasePage<BlockLogVo>> findBlockLogPage(@Validated BasePageAo basePageAo, @Validated BlockLogVo blockLogVo) {
        BasePage<BlockLogVo> page = this.blockLogService.findBlockLogPage(basePageAo, blockLogVo);
        return BaseRspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "黑名单拦截日志查询（集合）")
    @PreAuthorize("hasAuthority('blockLog:view')")
    public BaseRsp<List<BlockLogVo>> findBlockLogList(@Validated BlockLogVo blockLogVo) {
        List<BlockLogVo> list = this.blockLogService.findBlockLogList(blockLogVo);
        return BaseRspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "黑名单拦截日志查询（单个）")
    @PreAuthorize("hasAuthority('blockLog:view')")
    public BaseRsp<BlockLogVo> findBlockLog(@Validated BlockLogVo blockLogVo) {
        BlockLogVo blockLog = this.blockLogService.findBlockLog(blockLogVo);
        return BaseRspUtil.data(blockLog);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('blockLog:add')")
    @ControllerEndpoint(operation = "新增黑名单拦截日志")
    @Operation(summary = "新增黑名单拦截日志")
    public BaseRsp<BlockLog> addBlockLog(@RequestBody @Validated BlockLogAo blockLogAo) {
        return BaseRspUtil.data(this.blockLogService.createBlockLog(blockLogAo));
    }

    @DeleteMapping("/{blockLogIds}")
    @PreAuthorize("hasAuthority('blockLog:delete')")
    @ControllerEndpoint(operation = "删除黑名单拦截日志")
    @Operation(summary = "删除黑名单拦截日志")
    @Parameters({
            @Parameter(name = "blockLogIds", description = "黑名单拦截日志id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteBlockLog(@NotBlank(message = "{required}") @PathVariable String blockLogIds) {
        List<Long> ids = Arrays.stream(blockLogIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.blockLogService.deleteBlockLog(ids);
        return BaseRspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('blockLog:update')")
    @ControllerEndpoint(operation = "修改黑名单拦截日志")
    @Operation(summary = "修改黑名单拦截日志")
    public BaseRsp<BlockLog> updateBlockLog(@RequestBody @Validated(UpdateStrategy.class) BlockLogAo blockLogAo) {
        return BaseRspUtil.data(this.blockLogService.updateBlockLog(blockLogAo));
    }
}