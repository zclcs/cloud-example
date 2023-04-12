package com.zclcs.platform.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.RateLimitRule;
import com.zclcs.platform.system.api.entity.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.entity.vo.RateLimitRuleVo;

import java.util.List;

/**
 * 限流规则 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:39:49.113
 */
public interface RateLimitRuleService extends IService<RateLimitRule> {

    /**
     * 查询（分页）
     *
     * @param basePageAo      basePageAo
     * @param rateLimitRuleVo rateLimitRuleVo
     * @return BasePage<RateLimitRuleVo>
     */
    BasePage<RateLimitRuleVo> findRateLimitRulePage(BasePageAo basePageAo, RateLimitRuleVo rateLimitRuleVo);

    /**
     * 查询（所有）
     *
     * @param rateLimitRuleVo rateLimitRuleVo
     * @return List<RateLimitRuleVo>
     */
    List<RateLimitRuleVo> findRateLimitRuleList(RateLimitRuleVo rateLimitRuleVo);

    /**
     * 查询（单个）
     *
     * @param rateLimitRuleVo rateLimitRuleVo
     * @return RateLimitRuleVo
     */
    RateLimitRuleVo findRateLimitRule(RateLimitRuleVo rateLimitRuleVo);

    /**
     * 统计
     *
     * @param rateLimitRuleVo rateLimitRuleVo
     * @return RateLimitRuleVo
     */
    Integer countRateLimitRule(RateLimitRuleVo rateLimitRuleVo);

    /**
     * 缓存所有限流规则
     */
    void cacheAllRateLimitRules();

    /**
     * 新增
     *
     * @param rateLimitRuleAo rateLimitRuleAo
     * @return RateLimitRule
     */
    RateLimitRule createRateLimitRule(RateLimitRuleAo rateLimitRuleAo);

    /**
     * 修改
     *
     * @param rateLimitRuleAo rateLimitRuleAo
     * @return RateLimitRule
     */
    RateLimitRule updateRateLimitRule(RateLimitRuleAo rateLimitRuleAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteRateLimitRule(List<Long> ids);

}
