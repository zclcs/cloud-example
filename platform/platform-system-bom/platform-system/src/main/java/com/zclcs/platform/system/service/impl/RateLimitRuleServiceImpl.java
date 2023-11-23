package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.bean.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.bean.cache.RateLimitRuleCacheVo;
import com.zclcs.platform.system.api.bean.entity.RateLimitRule;
import com.zclcs.platform.system.api.bean.vo.RateLimitRuleVo;
import com.zclcs.platform.system.mapper.RateLimitRuleMapper;
import com.zclcs.platform.system.service.RateLimitRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.RateLimitRuleTableDef.RATE_LIMIT_RULE;

/**
 * 限流规则 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitRuleServiceImpl extends ServiceImpl<RateLimitRuleMapper, RateLimitRule> implements RateLimitRuleService {

    private final RedisService redisService;

    @Override
    public BasePage<RateLimitRuleVo> findRateLimitRulePage(BasePageAo basePageAo, RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper queryWrapper = getQueryWrapper(rateLimitRuleVo);
        Page<RateLimitRuleVo> rateLimitRuleVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, RateLimitRuleVo.class);
        return new BasePage<>(rateLimitRuleVoPage);
    }

    @Override
    public List<RateLimitRuleVo> findRateLimitRuleList(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.mapper.selectListByQueryAs(queryWrapper, RateLimitRuleVo.class);
    }

    @Override
    public RateLimitRuleVo findRateLimitRule(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, RateLimitRuleVo.class);
    }

    @Override
    public Long countRateLimitRule(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    @Override
    public void cacheAllRateLimitRules() {
        List<RateLimitRule> list = this.list();
        list.forEach(rateLimitRule -> {
            String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
            redisService.set(key, JsonUtil.toJson(RateLimitRuleCacheVo.convertToRateLimitRuleCacheBean(rateLimitRule)));
        });
    }

    private QueryWrapper getQueryWrapper(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        RATE_LIMIT_RULE.RATE_LIMIT_RULE_ID,
                        RATE_LIMIT_RULE.REQUEST_URI,
                        RATE_LIMIT_RULE.REQUEST_METHOD,
                        RATE_LIMIT_RULE.LIMIT_FROM,
                        RATE_LIMIT_RULE.LIMIT_TO,
                        RATE_LIMIT_RULE.RATE_LIMIT_COUNT,
                        RATE_LIMIT_RULE.INTERVAL_SEC,
                        RATE_LIMIT_RULE.RULE_STATUS,
                        RATE_LIMIT_RULE.CREATE_AT,
                        RATE_LIMIT_RULE.UPDATE_AT
                )
                .where(RATE_LIMIT_RULE.REQUEST_URI.like(rateLimitRuleVo.getRequestUri(), If::hasText))
                .and(RATE_LIMIT_RULE.REQUEST_METHOD.eq(rateLimitRuleVo.getRequestMethod()))
                .and(RATE_LIMIT_RULE.RULE_STATUS.eq(rateLimitRuleVo.getRuleStatus()))
        ;
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitRule createRateLimitRule(RateLimitRuleAo rateLimitRuleAo) {
        validateRateLimitRule(rateLimitRuleAo.getRequestUri(), rateLimitRuleAo.getRequestMethod(), rateLimitRuleAo.getRateLimitRuleId());
        RateLimitRule rateLimitRule = new RateLimitRule();
        BeanUtil.copyProperties(rateLimitRuleAo, rateLimitRule);
        this.save(rateLimitRule);
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
        redisService.set(key, JsonUtil.toJson(RateLimitRuleCacheVo.convertToRateLimitRuleCacheBean(rateLimitRule)));
        return rateLimitRule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitRule updateRateLimitRule(RateLimitRuleAo rateLimitRuleAo) {
        validateRateLimitRule(rateLimitRuleAo.getRequestUri(), rateLimitRuleAo.getRequestMethod(), rateLimitRuleAo.getRateLimitRuleId());
        RateLimitRule byId = this.getById(rateLimitRuleAo.getRateLimitRuleId());
        RateLimitRule rateLimitRule = new RateLimitRule();
        BeanUtil.copyProperties(rateLimitRuleAo, rateLimitRule);
        this.updateById(rateLimitRule);
        String oldKey = RouteEnhanceCacheUtil.getRateLimitCacheKey(byId.getRequestUri(), byId.getRequestMethod());
        String newKey = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
        redisService.del(oldKey);
        redisService.set(newKey, JsonUtil.toJson(RateLimitRuleCacheVo.convertToRateLimitRuleCacheBean(rateLimitRule)));
        return rateLimitRule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRateLimitRule(List<Long> ids) {
        List<RateLimitRule> list = this.list(new QueryWrapper().where(RATE_LIMIT_RULE.RATE_LIMIT_RULE_ID.in(ids)));
        this.removeByIds(ids);
        for (RateLimitRule rateLimitRule : list) {
            String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
            redisService.del(key);
        }
    }

    @Override
    public void validateRateLimitRule(String requestUri, String requestMethod, Long rateLimitRuleId) {
        RateLimitRule byId = this.getOne(new QueryWrapper()
                .where(RATE_LIMIT_RULE.REQUEST_URI.eq(requestUri))
                .and(RATE_LIMIT_RULE.REQUEST_METHOD.eq(requestMethod))
        );
        if (byId != null && !byId.getRateLimitRuleId().equals(rateLimitRuleId)) {
            throw new FieldException("限流规则重复");
        }
    }
}
