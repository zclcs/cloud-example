package com.zclcs.platform.system.service;

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
 * @since 2023-09-01 19:53:43.828
 */
public interface RateLimitRuleService extends IService<RateLimitRule> {

    /**
     * 查询（分页）
     *
     * @param basePageAo      {@link BasePageAo}
     * @param rateLimitRuleVo {@link RateLimitRuleVo}
     * @return {@link RateLimitRuleVo}
     */
    BasePage<RateLimitRuleVo> findRateLimitRulePage(BasePageAo basePageAo, RateLimitRuleVo rateLimitRuleVo);

    /**
     * 查询（所有）
     *
     * @param rateLimitRuleVo {@link RateLimitRuleVo}
     * @return {@link RateLimitRuleVo}
     */
    List<RateLimitRuleVo> findRateLimitRuleList(RateLimitRuleVo rateLimitRuleVo);

    /**
     * 查询（单个）
     *
     * @param rateLimitRuleVo {@link RateLimitRuleVo}
     * @return {@link RateLimitRuleVo}
     */
    RateLimitRuleVo findRateLimitRule(RateLimitRuleVo rateLimitRuleVo);

    /**
     * 统计
     *
     * @param rateLimitRuleVo {@link RateLimitRuleVo}
     * @return 统计值
     */
    Long countRateLimitRule(RateLimitRuleVo rateLimitRuleVo);

    /**
     * 缓存所有限流规则
     */
    void cacheAllRateLimitRules();

    /**
     * 新增
     *
     * @param rateLimitRuleAo {@link RateLimitRuleAo}
     * @return {@link RateLimitRule}
     */
    RateLimitRule createRateLimitRule(RateLimitRuleAo rateLimitRuleAo);

    /**
     * 修改
     *
     * @param rateLimitRuleAo {@link RateLimitRuleAo}
     * @return {@link RateLimitRule}
     */
    RateLimitRule updateRateLimitRule(RateLimitRuleAo rateLimitRuleAo);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteRateLimitRule(List<Long> ids);

}
