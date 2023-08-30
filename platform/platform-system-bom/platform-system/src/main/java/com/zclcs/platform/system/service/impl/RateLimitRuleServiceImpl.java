package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
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
        BasePage<RateLimitRuleVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.mapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RateLimitRuleVo> findRateLimitRuleList(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.mapper.findListVo(queryWrapper);
    }

    @Override
    public RateLimitRuleVo findRateLimitRule(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.mapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRateLimitRule(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.mapper.countVo(queryWrapper);
    }

    @Override
    public void cacheAllRateLimitRules() {
        List<RateLimitRule> list = this.lambdaQuery().list();
        list.forEach(rateLimitRule -> {
            String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
            try {
                redisService.set(key, objectMapper.writeValueAsString(RateLimitRuleCacheBean.convertToRateLimitRuleCacheBean(rateLimitRule)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private QueryWrapper<RateLimitRuleVo> getQueryWrapper(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeRightNotBlank(queryWrapper, "srlr.request_uri", rateLimitRuleVo.getRequestUri());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "srlr.request_method", rateLimitRuleVo.getRequestMethod());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "srlr.rule_status", rateLimitRuleVo.getRuleStatus());
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
        List<RateLimitRule> list = this.lambdaQuery().eq(RateLimitRule::getRateLimitRuleId, ids).list();
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
