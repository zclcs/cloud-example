package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.RateLimitRule;
import com.zclcs.platform.system.api.entity.ao.RateLimitRuleAo;
import com.zclcs.platform.system.api.entity.vo.RateLimitRuleVo;
import com.zclcs.platform.system.biz.mapper.RateLimitRuleMapper;
import com.zclcs.platform.system.biz.service.RateLimitRuleService;
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
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class RateLimitRuleServiceImpl extends ServiceImpl<RateLimitRuleMapper, RateLimitRule> implements RateLimitRuleService {

    @Override
    public BasePage<RateLimitRuleVo> findRateLimitRulePage(BasePageAo basePageAo, RateLimitRuleVo rateLimitRuleVo) {
        BasePage<RateLimitRuleVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RateLimitRuleVo> findRateLimitRuleList(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public RateLimitRuleVo findRateLimitRule(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRateLimitRule(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = getQueryWrapper(rateLimitRuleVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<RateLimitRuleVo> getQueryWrapper(RateLimitRuleVo rateLimitRuleVo) {
        QueryWrapper<RateLimitRuleVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitRule createRateLimitRule(RateLimitRuleAo rateLimitRuleAo) {
        RateLimitRule rateLimitRule = new RateLimitRule();
        BeanUtil.copyProperties(rateLimitRuleAo, rateLimitRule);
        this.save(rateLimitRule);
        return rateLimitRule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitRule updateRateLimitRule(RateLimitRuleAo rateLimitRuleAo) {
        RateLimitRule rateLimitRule = new RateLimitRule();
        BeanUtil.copyProperties(rateLimitRuleAo, rateLimitRule);
        this.updateById(rateLimitRule);
        return rateLimitRule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRateLimitRule(List<Long> ids) {
        this.removeByIds(ids);
    }
}
