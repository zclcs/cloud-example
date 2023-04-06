package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.AddStrategy;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.platform.system.api.entity.MinioBucket;
import com.zclcs.platform.system.api.entity.ao.MinioBucketAo;
import com.zclcs.platform.system.api.entity.vo.MinioBucketVo;
import com.zclcs.platform.system.biz.service.MinioBucketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
 * 桶 Controller
 *
 * @author zclcs
 * @date 2021-10-18 10:37:09.922
 */
@Slf4j
@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
@Tag(name = "桶")
public class MinioBucketController {

    private final MinioBucketService minioBucketService;

    @GetMapping
    @Operation(summary = "桶查询（分页）")
    @PreAuthorize("hasAuthority('bucket:view')")
    public BaseRsp<BasePage<MinioBucketVo>> findMinioBucketPage(@Valid BasePageAo basePageAo, MinioBucketVo minioBucketVo) {
        BasePage<MinioBucketVo> page = this.minioBucketService.findMinioBucketPage(basePageAo, minioBucketVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @Operation(summary = "桶查询（集合）")
    @PreAuthorize("hasAuthority('bucket:view')")
    public BaseRsp<List<MinioBucketVo>> findMinioBucketList(MinioBucketVo minioBucketVo) {
        List<MinioBucketVo> list = this.minioBucketService.findMinioBucketList(minioBucketVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @Operation(summary = "桶查询（单个）")
    @PreAuthorize("hasAuthority('bucket:view')")
    public BaseRsp<MinioBucketVo> findMinioBucket(MinioBucketVo minioBucketVo) {
        MinioBucketVo minioBucket = this.minioBucketService.findMinioBucket(minioBucketVo);
        return RspUtil.data(minioBucket);
    }

    @GetMapping("/checkBucketName")
    @PreAuthorize("hasAnyAuthority('bucket:add', 'bucket:update')")
    @Operation(summary = "检查桶名称")
    @Parameters({
            @Parameter(name = "bucketId", description = "桶id", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "bucketName", description = "桶名称", required = true, in = ParameterIn.QUERY)
    })
    public BaseRsp<Object> checkBucketName(@RequestParam(required = false) Long bucketId,
                                           @NotBlank(message = "{required}") @RequestParam String bucketName) {
        minioBucketService.validateBucketName(bucketName, bucketId);
        return RspUtil.message();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('bucket:add')")
    @Operation(summary = "新增桶")
    @ControllerEndpoint(operation = "新增桶")
    public BaseRsp<MinioBucket> addMinioBucket(@RequestBody @Validated(AddStrategy.class) MinioBucketAo minioBucketAo) {
        return RspUtil.data(this.minioBucketService.createMinioBucket(minioBucketAo));
    }

    @DeleteMapping("/{bucketIds}")
    @PreAuthorize("hasAuthority('bucket:delete')")
    @ControllerEndpoint(operation = "删除桶")
    @Operation(summary = "删除桶")
    @Parameters({
            @Parameter(name = "bucketIds", description = "桶id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteMinioBucket(@NotBlank(message = "{required}") @PathVariable String bucketIds) {
        List<Long> ids = Arrays.stream(bucketIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.minioBucketService.deleteMinioBucket(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('bucket:update')")
    @Operation(summary = "修改桶")
    @ControllerEndpoint(operation = "修改桶")
    public BaseRsp<MinioBucket> updateMinioBucket(@RequestBody @Validated(UpdateStrategy.class) MinioBucketAo minioBucketAo) {
        return RspUtil.data(this.minioBucketService.updateMinioBucket(minioBucketAo));
    }

}