package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.AddStrategy;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.MinioBucketAo;
import com.zclcs.platform.system.api.bean.entity.MinioBucket;
import com.zclcs.platform.system.api.bean.vo.MinioBucketVo;
import com.zclcs.platform.system.service.MinioService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 桶
 *
 * @author zclcs
 * @since 2021-10-18 10:37:09.922
 */
@Slf4j
@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class MinioBucketController {

    private final MinioService minioService;

    /**
     * 桶查询（分页）
     * 权限: bucket:view
     *
     * @see MinioService#findMinioBucketPage(BasePageAo, MinioBucketVo)
     */
    @GetMapping
    @SaCheckPermission("bucket:view")
    public BaseRsp<BasePage<MinioBucketVo>> findMinioBucketPage(@Validated BasePageAo basePageAo, @Validated MinioBucketVo minioBucketVo) {
        BasePage<MinioBucketVo> page = this.minioService.findMinioBucketPage(basePageAo, minioBucketVo);
        return RspUtil.data(page);
    }

    /**
     * 桶查询（集合）
     * 权限: bucket:view
     *
     * @see MinioService#findMinioBucketList(MinioBucketVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("bucket:view")
    public BaseRsp<List<MinioBucketVo>> findMinioBucketList(@Validated MinioBucketVo minioBucketVo) {
        List<MinioBucketVo> list = this.minioService.findMinioBucketList(minioBucketVo);
        return RspUtil.data(list);
    }

    /**
     * 桶查询（单个）
     * 权限: bucket:view
     *
     * @see MinioService#findMinioBucket(MinioBucketVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("bucket:view")
    public BaseRsp<MinioBucketVo> findMinioBucket(@Validated MinioBucketVo minioBucketVo) {
        MinioBucketVo minioBucket = this.minioService.findMinioBucket(minioBucketVo);
        return RspUtil.data(minioBucket);
    }

    /**
     * 检查桶名称
     * 权限: bucket:add 或者 bucket:update
     *
     * @param bucketId   桶id
     * @param bucketName 桶名称
     * @return 是否重复
     */
    @GetMapping("/checkBucketName")
    @SaCheckPermission(value = {"bucket:add", "bucket:update"}, mode = SaMode.OR)
    public BaseRsp<Object> checkBucketName(@RequestParam(required = false) Long bucketId,
                                           @NotBlank(message = "{required}") @RequestParam String bucketName) {
        minioService.validateBucketName(bucketName, bucketId);
        return RspUtil.message();
    }

    /**
     * 新增桶
     * 权限: bucket:add
     *
     * @see MinioService#createMinioBucket(MinioBucketAo)
     */
    @PostMapping
    @SaCheckPermission("bucket:add")
    @ControllerEndpoint(operation = "新增桶")
    public BaseRsp<MinioBucket> addMinioBucket(@RequestBody @Validated(AddStrategy.class) MinioBucketAo minioBucketAo) {
        return RspUtil.data(this.minioService.createMinioBucket(minioBucketAo));
    }

    /**
     * 删除桶
     * 权限: bucket:delete
     *
     * @see MinioService#deleteMinioBucket(List)
     */
    @DeleteMapping("/{bucketIds}")
    @SaCheckPermission("bucket:delete")
    @ControllerEndpoint(operation = "删除桶")
    public BaseRsp<String> deleteMinioBucket(@NotBlank(message = "{required}") @PathVariable String bucketIds) {
        List<Long> ids = Arrays.stream(bucketIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.minioService.deleteMinioBucket(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改桶
     * 权限: bucket:update
     *
     * @see MinioService#updateMinioBucket(MinioBucketAo)
     */
    @PutMapping
    @SaCheckPermission("bucket:update")
    @ControllerEndpoint(operation = "修改桶")
    public BaseRsp<MinioBucket> updateMinioBucket(@RequestBody @Validated(UpdateStrategy.class) MinioBucketAo minioBucketAo) {
        return RspUtil.data(this.minioService.updateMinioBucket(minioBucketAo));
    }

}