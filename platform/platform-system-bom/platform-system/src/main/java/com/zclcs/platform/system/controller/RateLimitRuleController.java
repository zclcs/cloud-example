package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.platform.system.api.bean.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.bean.entity.RateLimitRule;
import com.zclcs.platform.system.api.bean.vo.RateLimitRuleVo;
import com.zclcs.platform.system.service.RateLimitRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
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
@RequestMapping("/rateLimitRule")
@RequiredArgsConstructor
@Tag(name = "限流规则")
public class RateLimitRuleController {

    private final RateLimitRuleService rateLimitRuleService;

    @GetMapping
    @SaCheckPermission("rateLimitRule:view")
    @Operation(summary = "限流规则查询（分页）")
    public BaseRsp<BasePage<RateLimitRuleVo>> findRateLimitRulePage(@ParameterObject @Validated BasePageAo basePageAo, @ParameterObject @Validated RateLimitRuleVo rateLimitRuleVo) {
        BasePage<RateLimitRuleVo> page = this.rateLimitRuleService.findRateLimitRulePage(basePageAo, rateLimitRuleVo);
        return RspUtil.data(page);
    }

    @GetMapping("/list")
    @SaCheckPermission("rateLimitRule:view")
    @Operation(summary = "限流规则查询（集合）")
    public BaseRsp<List<RateLimitRuleVo>> findRateLimitRuleList(@ParameterObject @Validated RateLimitRuleVo rateLimitRuleVo) {
        List<RateLimitRuleVo> list = this.rateLimitRuleService.findRateLimitRuleList(rateLimitRuleVo);
        return RspUtil.data(list);
    }

    @GetMapping("/one")
    @SaCheckPermission("rateLimitRule:view")
    @Operation(summary = "限流规则查询（单个）")
    public BaseRsp<RateLimitRuleVo> findRateLimitRule(@ParameterObject @Validated RateLimitRuleVo rateLimitRuleVo) {
        RateLimitRuleVo rateLimitRule = this.rateLimitRuleService.findRateLimitRule(rateLimitRuleVo);
        return RspUtil.data(rateLimitRule);
    }

    @PostMapping
    @SaCheckPermission("rateLimitRule:add")
    @ControllerEndpoint(operation = "新增限流规则")
    @Operation(summary = "新增限流规则")
    public BaseRsp<RateLimitRule> addRateLimitRule(@RequestBody @Validated RateLimitRuleAo rateLimitRuleAo) {
        return RspUtil.data(this.rateLimitRuleService.createRateLimitRule(rateLimitRuleAo));
    }

    @DeleteMapping("/{rateLimitRuleIds}")
    @SaCheckPermission("rateLimitRule:delete")
    @ControllerEndpoint(operation = "删除限流规则")
    @Operation(summary = "删除限流规则")
    @Parameters({
            @Parameter(name = "rateLimitRuleIds", description = "限流规则id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> deleteRateLimitRule(@NotBlank(message = "{required}") @PathVariable String rateLimitRuleIds) {
        List<Long> ids = Arrays.stream(rateLimitRuleIds.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.rateLimitRuleService.deleteRateLimitRule(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @SaCheckPermission("rateLimitRule:update")
    @ControllerEndpoint(operation = "修改限流规则")
    @Operation(summary = "修改限流规则")
    public BaseRsp<RateLimitRule> updateRateLimitRule(@RequestBody @Validated(UpdateStrategy.class) RateLimitRuleAo rateLimitRuleAo) {
        return RspUtil.data(this.rateLimitRuleService.updateRateLimitRule(rateLimitRuleAo));
    }
}