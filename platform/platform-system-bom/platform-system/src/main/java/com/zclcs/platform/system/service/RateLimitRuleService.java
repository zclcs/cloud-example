package com.zclcs.platform.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.bean.entity.RateLimitRule;
import com.zclcs.platform.system.api.bean.vo.RateLimitRuleVo;

import java.util.List;

/**
 * 限流规则 Service接口
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
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
    Long countRateLimitRule(RateLimitRuleVo rateLimitRuleVo);

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
    RateLimitRule createRateLimitRule(RateLimitRuleAo rateLimitRuleAo) throws JsonProcessingException;

    /**
     * 修改
     *
     * @param rateLimitRuleAo rateLimitRuleAo
     * @return RateLimitRule
     */
    RateLimitRule updateRateLimitRule(RateLimitRuleAo rateLimitRuleAo) throws JsonProcessingException;

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteRateLimitRule(List<Long> ids);

}
