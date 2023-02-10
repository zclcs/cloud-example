package com.zclcs.platform.system.biz.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import com.zclcs.platform.system.api.entity.RateLimitRule;
import com.zclcs.platform.system.api.entity.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.entity.vo.RateLimitRuleVo;
import com.zclcs.platform.system.biz.service.RateLimitRuleService;
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
 * 限流规则 Controller
 *
 * @author zclcs
 * @date 2023-01-10 10:39:49.113
 */
@Slf4j
@RestController
@RequestMapping("rateLimitRule")
@RequiredArgsConstructor
@Tag(name = "限流规则")
public class RateLimitRuleController {

    private final RateLimitRuleService rateLimitRuleService;

    @GetMapping
    @Operation(summary = "限流规则查询（分页）")
    @PreAuthorize("hasAuthority('rateLimitRule:view')")
    public BaseRsp<BasePage<RateLimitRuleVo>> findRateLimitRulePage(@Validated BasePageAo basePageAo, @Validated RateLimitRuleVo rateLimitRuleVo) {
        BasePage<RateLimitRuleVo> page = this.rateLimitRuleService.findRateLimitRulePage(basePageAo, rateLimitRuleVo);
        return RspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "限流规则查询（集合）")
    @PreAuthorize("hasAuthority('rateLimitRule:view')")
    public BaseRsp<List<RateLimitRuleVo>> findRateLimitRuleList(@Validated RateLimitRuleVo rateLimitRuleVo) {
        List<RateLimitRuleVo> list = this.rateLimitRuleService.findRateLimitRuleList(rateLimitRuleVo);
        return RspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "限流规则查询（单个）")
    @PreAuthorize("hasAuthority('rateLimitRule:view')")
    public BaseRsp<RateLimitRuleVo> findRateLimitRule(@Validated RateLimitRuleVo rateLimitRuleVo) {
        RateLimitRuleVo rateLimitRule = this.rateLimitRuleService.findRateLimitRule(rateLimitRuleVo);
        return RspUtil.data(rateLimitRule);
    }

    @GetMapping("refresh")
    @Operation(summary = "刷新限流规则缓存")
    public BaseRsp<Object> refresh() {
        this.rateLimitRuleService.cacheAllRateLimitRules();
        return RspUtil.message();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('rateLimitRule:add')")
    @ControllerEndpoint(operation = "新增限流规则")
    @Operation(summary = "新增限流规则")
    public BaseRsp<RateLimitRule> addRateLimitRule(@RequestBody @Validated RateLimitRuleAo rateLimitRuleAo) {
        return RspUtil.data(this.rateLimitRuleService.createRateLimitRule(rateLimitRuleAo));
    }

    @DeleteMapping("/{rateLimitRuleIds}")
    @PreAuthorize("hasAuthority('rateLimitRule:delete')")
    @ControllerEndpoint(operation = "删除限流规则")
    @Operation(summary = "删除限流规则")
    @Parameters({
            @Parameter(name = "rateLimitRuleIds", description = "限流规则id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRateLimitRule(@NotBlank(message = "{required}") @PathVariable String rateLimitRuleIds) {
        List<Long> ids = Arrays.stream(rateLimitRuleIds.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.rateLimitRuleService.deleteRateLimitRule(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('rateLimitRule:update')")
    @ControllerEndpoint(operation = "修改限流规则")
    @Operation(summary = "修改限流规则")
    public BaseRsp<RateLimitRule> updateRateLimitRule(@RequestBody @Validated(UpdateStrategy.class) RateLimitRuleAo rateLimitRuleAo) {
        return RspUtil.data(this.rateLimitRuleService.updateRateLimitRule(rateLimitRuleAo));
    }
}