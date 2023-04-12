package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.utils.RouteEnhanceCacheUtil;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.system.api.entity.RateLimitRule;
import com.zclcs.platform.system.api.entity.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.entity.vo.RateLimitRuleVo;
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
 * @date 2023-01-10 10:39:49.113
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RateLimitRuleServiceImpl extends ServiceImpl<RateLimitRuleMapper, RateLimitRule> implements RateLimitRuleService {

    private final RedisService redisService;

    @Override
    public BasePage<RateLimitRuleVo> findRateLimitRulePage(BasePageAo basePageAo, RateLimitRuleVo rateLimitRuleVo) {
        BasePage<RateLimitRuleVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RateLimitRuleVo> findRateLimitRuleList(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public RateLimitRuleVo findRateLimitRule(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRateLimitRule(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    @Override
    public void cacheAllRateLimitRules() {
        List<RateLimitRule> list = this.lambdaQuery().list();
        list.forEach(rateLimitRule -> {
            String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
            this.setCacheRateLimitRule(rateLimitRule);
            redisService.set(key, rateLimitRule);
        });
        log.info("Cache rate limit rules into redis >>>");
    }

    private QueryWrapper<RateLimitRuleVo> getQueryWrapper(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "srlr.request_uri", rateLimitRuleVo.getRequestUri());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "srlr.request_method", rateLimitRuleVo.getRequestMethod());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "srlr.rule_status", rateLimitRuleVo.getRuleStatus());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitRule createRateLimitRule(RateLimitRuleAo rateLimitRuleAo) {
        RateLimitRule rateLimitRule = new RateLimitRule();
        BeanUtil.copyProperties(rateLimitRuleAo, rateLimitRule);
        setRateLimitRule(rateLimitRule);
        this.save(rateLimitRule);
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
        this.setCacheRateLimitRule(rateLimitRule);
        redisService.set(key, rateLimitRule);
        return rateLimitRule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitRule updateRateLimitRule(RateLimitRuleAo rateLimitRuleAo) {
        RateLimitRule rateLimitRule = new RateLimitRule();
        BeanUtil.copyProperties(rateLimitRuleAo, rateLimitRule);
        setRateLimitRule(rateLimitRule);
        this.updateById(rateLimitRule);
        String key = RouteEnhanceCacheUtil.getRateLimitCacheKey(rateLimitRule.getRequestUri(), rateLimitRule.getRequestMethod());
        redisService.set(key, rateLimitRule);
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

    private void setCacheRateLimitRule(RateLimitRule rule) {
        rule.setCreateAt(null);
        rule.setUpdateAt(null);
    }

    private void setRateLimitRule(RateLimitRule rateLimitRule) {
        if (StrUtil.isNotBlank(rateLimitRule.getLimitFrom()) && StrUtil.isNotBlank(rateLimitRule.getLimitTo())) {
            rateLimitRule.setLimitFrom(DateUtil.parse(rateLimitRule.getLimitFrom()).toString(DatePattern.NORM_TIME_PATTERN));
            rateLimitRule.setLimitTo(DateUtil.parse(rateLimitRule.getLimitTo()).toString(DatePattern.NORM_TIME_PATTERN));
        }
    }
}
