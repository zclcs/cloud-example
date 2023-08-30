package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.bean.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.bean.cache.RateLimitRuleCacheBean;
import com.zclcs.platform.system.api.bean.entity.RateLimitRule;
import com.zclcs.platform.system.api.bean.vo.RateLimitRuleVo;
import com.zclcs.platform.system.mapper.RateLimitRuleMapper;
import com.zclcs.platform.system.service.RateLimitRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RateLimitRuleServiceImpl extends ServiceImpl<RateLimitRuleMapper, RateLimitRule> implements RateLimitRuleService {

    private final RedisService redisService;
    private final ObjectMapper objectMapper;

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
            try {
                redisService.set(key, objectMapper.writeValueAsString(RateLimitRuleCacheBean.convertToRateLimitRuleCacheBean(rateLimitRule)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
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
                .where(RATE_LIMIT_RULE.REQUEST_URI.likeRight(rateLimitRuleVo.getRequestUri(), If::hasText))
                .and(RATE_LIMIT_RULE.REQUEST_METHOD.eq(rateLimitRuleVo.getRequestMethod()))
                .and(RATE_LIMIT_RULE.RULE_STATUS.eq(rateLimitRuleVo.getRuleStatus()))
        ;
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitRule createRateLimitRule(RateLimitRuleAo rateLimitRuleAo) throws JsonProcessingException {
        RateLimitRule rateLimitRule = new RateLimitRule();
        BeanUtil.copyProperties(rateLimitRuleAo, rateLimitRule);
        setRateLimitRule(rateLimitRule);
        this.save(rateLimitRule);
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
        redisService.set(key, objectMapper.writeValueAsString(RateLimitRuleCacheBean.convertToRateLimitRuleCacheBean(rateLimitRule)));
        return rateLimitRule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitRule updateRateLimitRule(RateLimitRuleAo rateLimitRuleAo) throws JsonProcessingException {
        RateLimitRule rateLimitRule = new RateLimitRule();
        BeanUtil.copyProperties(rateLimitRuleAo, rateLimitRule);
        setRateLimitRule(rateLimitRule);
        this.updateById(rateLimitRule);
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
        redisService.set(key, objectMapper.writeValueAsString(RateLimitRuleCacheBean.convertToRateLimitRuleCacheBean(rateLimitRule)));
        return rateLimitRule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRateLimitRule(List<Long> ids) {
        List<RateLimitRule> list = this.queryChain().where(RATE_LIMIT_RULE.RATE_LIMIT_RULE_ID.in(ids)).list();
        this.removeByIds(ids);
        for (RateLimitRule rateLimitRule : list) {
            String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
            redisService.del(key);
        }
    }

    private void setRateLimitRule(RateLimitRule rateLimitRule) {
        if (StrUtil.isBlank(rateLimitRule.getLimitFrom())) {
            rateLimitRule.setLimitFrom("00:00:00");
        }
        if (StrUtil.isBlank(rateLimitRule.getLimitTo())) {
            rateLimitRule.setLimitFrom("23:59:59");
        }
    }
}
