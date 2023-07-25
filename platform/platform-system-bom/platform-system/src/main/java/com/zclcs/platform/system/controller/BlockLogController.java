package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.BlockLogAo;
import com.zclcs.platform.system.api.bean.entity.BlockLog;
import com.zclcs.platform.system.api.bean.vo.BlockLogVo;
import com.zclcs.platform.system.service.BlockLogService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 黑名单拦截日志
 *
 * @author zclcs
 * @date 2023-01-10 10:40:05.798
 */
@Slf4j
@RestController
@RequestMapping("/blockLog")
@RequiredArgsConstructor
public class BlockLogController {

    private final BlockLogService blockLogService;

    /**
     * 黑名单拦截日志查询（分页）
     * 权限: blockLog:view
     *
     * @see BlockLogService#findBlockLogPage(BasePageAo, BlockLogVo)
     */
    @GetMapping
    @SaCheckPermission("blockLog:view")
    public BaseRsp<BasePage<BlockLogVo>> findBlockLogPage(@Validated BasePageAo basePageAo, @Validated BlockLogVo blockLogVo) {
        BasePage<BlockLogVo> page = this.blockLogService.findBlockLogPage(basePageAo, blockLogVo);
        return RspUtil.data(page);
    }

    /**
     * 黑名单拦截日志查询（集合）
     * 权限: blockLog:view
     *
     * @see BlockLogService#findBlockLogList(BlockLogVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("blockLog:view")
    public BaseRsp<List<BlockLogVo>> findBlockLogList(@Validated BlockLogVo blockLogVo) {
        List<BlockLogVo> list = this.blockLogService.findBlockLogList(blockLogVo);
        return RspUtil.data(list);
    }


    /**
     * 黑名单拦截日志查询（单个）
     * 权限: blockLog:view
     *
     * @see BlockLogService#findBlockLog(BlockLogVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("blockLog:view")
    public BaseRsp<BlockLogVo> findBlockLog(@Validated BlockLogVo blockLogVo) {
        BlockLogVo blockLog = this.blockLogService.findBlockLog(blockLogVo);
        return RspUtil.data(blockLog);
    }

    /**
     * 新增黑名单拦截日志
     * 权限: blockLog:add
     *
     * @see BlockLogService#createBlockLog(BlockLogAo)
     */
    @PostMapping
    @SaCheckPermission("blockLog:add")
    @ControllerEndpoint(operation = "新增黑名单拦截日志")
    public BaseRsp<BlockLog> addBlockLog(@RequestBody @Validated BlockLogAo blockLogAo) {
        return RspUtil.data(this.blockLogService.createBlockLog(blockLogAo));
    }


    /**
     * 删除黑名单拦截日志
     * 权限: blockLog:delete
     *
     * @param blockLogIds 黑名单拦截日志id集合(,分隔)
     * @see BlockLogService#deleteBlockLog(List)
     */
    @DeleteMapping("/{blockLogIds}")
    @SaCheckPermission("blockLog:delete")
    @ControllerEndpoint(operation = "删除黑名单拦截日志")
    public BaseRsp<String> deleteBlockLog(@NotBlank(message = "{required}") @PathVariable String blockLogIds) {
        List<Long> ids = Arrays.stream(blockLogIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.blockLogService.deleteBlockLog(ids);
        return RspUtil.message("删除成功");
    }
}