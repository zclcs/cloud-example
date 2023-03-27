package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.platform.system.api.entity.BlackList;
import com.zclcs.platform.system.api.entity.ao.BlackListAo;
import com.zclcs.platform.system.api.entity.vo.BlackListVo;
import com.zclcs.platform.system.biz.service.BlackListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 黑名单 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:40:14.628
 */
@Slf4j
@RestController
@RequestMapping("/blackList")
@RequiredArgsConstructor
@Tag(name = "黑名单")
public class BlackListController {

    private final BlackListService blackListService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "黑名单查询（分页）")
    @PreAuthorize("hasAuthority('blackList:view')")
    public BaseRsp<BasePage<BlackListVo>> findBlackListPage(@Validated BasePageAo basePageAo, @Validated BlackListVo blackListVo) {
        BasePage<BlackListVo> page = this.blackListService.findBlackListPage(basePageAo, blackListVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @Operation(summary = "黑名单查询（集合）")
    @PreAuthorize("hasAuthority('blackList:view')")
    public BaseRsp<List<BlackListVo>> findBlackListList(@Validated BlackListVo blackListVo) {
        List<BlackListVo> list = this.blackListService.findBlackListList(blackListVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @Operation(summary = "黑名单查询（单个）")
    @PreAuthorize("hasAuthority('blackList:view')")
    public BaseRsp<BlackListVo> findBlackList(@Validated BlackListVo blackListVo) {
        BlackListVo blackList = this.blackListService.findBlackList(blackListVo);
        return RspUtil.data(blackList);
    }

    @GetMapping("/refresh")
    @Operation(summary = "刷新黑名单缓存")
    public BaseRsp<Object> refresh() {
        this.blackListService.cacheAllBlackList();
        return RspUtil.message();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('blackList:add')")
    @ControllerEndpoint(operation = "新增黑名单")
    @Operation(summary = "新增黑名单")
    public BaseRsp<BlackList> addBlackList(@RequestBody @Validated BlackListAo blackListAo) {
        return RspUtil.data(this.blackListService.createBlackList(blackListAo));
    }

    @DeleteMapping("/{blackListIds}")
    @PreAuthorize("hasAuthority('blackList:delete')")
    @ControllerEndpoint(operation = "删除黑名单")
    @Operation(summary = "删除黑名单")
    @Parameters({
            @Parameter(name = "blackListIds", description = "黑名单id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteBlackList(@NotBlank(message = "{required}") @PathVariable String blackListIds) {
        List<Long> ids = Arrays.stream(blackListIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.blackListService.deleteBlackList(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('blackList:update')")
    @ControllerEndpoint(operation = "修改黑名单")
    @Operation(summary = "修改黑名单")
    public BaseRsp<BlackList> updateBlackList(@RequestBody @Validated(UpdateStrategy.class) BlackListAo blackListAo) {
        return RspUtil.data(this.blackListService.updateBlackList(blackListAo));
    }
}