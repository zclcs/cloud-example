package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.bean.entity.RateLimitRule;
import com.zclcs.platform.system.api.bean.vo.RateLimitRuleVo;
import com.zclcs.platform.system.service.RateLimitRuleService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 限流规则
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
 */
@Slf4j
@RestController
@RequestMapping("/rateLimitRule")
@RequiredArgsConstructor
public class RateLimitRuleController {

    private final RateLimitRuleService rateLimitRuleService;

    /**
     * 限流规则查询（分页）
     * 权限: rateLimitRule:view
     *
     * @see RateLimitRuleService#findRateLimitRulePage(BasePageAo, RateLimitRuleVo)
     */
    @GetMapping
    @SaCheckPermission("rateLimitRule:view")
    public BaseRsp<BasePage<RateLimitRuleVo>> findRateLimitRulePage(@Validated BasePageAo basePageAo, @Validated RateLimitRuleVo rateLimitRuleVo) {
        BasePage<RateLimitRuleVo> page = this.rateLimitRuleService.findRateLimitRulePage(basePageAo, rateLimitRuleVo);
        return RspUtil.data(page);
    }

    /**
     * 限流规则查询（集合）
     * 权限: rateLimitRule:view
     *
     * @see RateLimitRuleService#findRateLimitRuleList(RateLimitRuleVo)
     */
    @GetMapping("/list")
    @SaCheckPermission("rateLimitRule:view")
    public BaseRsp<List<RateLimitRuleVo>> findRateLimitRuleList(@Validated RateLimitRuleVo rateLimitRuleVo) {
        List<RateLimitRuleVo> list = this.rateLimitRuleService.findRateLimitRuleList(rateLimitRuleVo);
        return RspUtil.data(list);
    }

    /**
     * 限流规则查询（单个）
     * 权限: rateLimitRule:view
     *
     * @see RateLimitRuleService#findRateLimitRule(RateLimitRuleVo)
     */
    @GetMapping("/one")
    @SaCheckPermission("rateLimitRule:view")
    public BaseRsp<RateLimitRuleVo> findRateLimitRule(@Validated RateLimitRuleVo rateLimitRuleVo) {
        RateLimitRuleVo rateLimitRule = this.rateLimitRuleService.findRateLimitRule(rateLimitRuleVo);
        return RspUtil.data(rateLimitRule);
    }

    /**
     * 新增限流规则
     * 权限: rateLimitRule:add
     *
     * @see RateLimitRuleService#createRateLimitRule(RateLimitRuleAo)
     */
    @PostMapping
    @SaCheckPermission("rateLimitRule:add")
    @ControllerEndpoint(operation = "新增限流规则")
    public BaseRsp<RateLimitRule> addRateLimitRule(@RequestBody @Validated RateLimitRuleAo rateLimitRuleAo) throws JsonProcessingException {
        return RspUtil.data(this.rateLimitRuleService.createRateLimitRule(rateLimitRuleAo));
    }

    /**
     * 删除限流规则
     * 权限: rateLimitRule:delete
     *
     * @param rateLimitRuleIds 限流规则id集合(,分隔)
     * @see RateLimitRuleService#deleteRateLimitRule(List)
     */
    @DeleteMapping("/{rateLimitRuleIds}")
    @SaCheckPermission("rateLimitRule:delete")
    @ControllerEndpoint(operation = "删除限流规则")
    public BaseRsp<String> deleteRateLimitRule(@NotBlank(message = "{required}") @PathVariable String rateLimitRuleIds) {
        List<Long> ids = Arrays.stream(rateLimitRuleIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.rateLimitRuleService.deleteRateLimitRule(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 修改限流规则
     * 权限: rateLimitRule:update
     *
     * @see RateLimitRuleService#updateRateLimitRule(RateLimitRuleAo)
     */
    @PutMapping
    @SaCheckPermission("rateLimitRule:update")
    @ControllerEndpoint(operation = "修改限流规则")
    public BaseRsp<RateLimitRule> updateRateLimitRule(@RequestBody @Validated({ValidGroups.Crud.Update.class}) RateLimitRuleAo rateLimitRuleAo) throws JsonProcessingException {
        return RspUtil.data(this.rateLimitRuleService.updateRateLimitRule(rateLimitRuleAo));
    }
}