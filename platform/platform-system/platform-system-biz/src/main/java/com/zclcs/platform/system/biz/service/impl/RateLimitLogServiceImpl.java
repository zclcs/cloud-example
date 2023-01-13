package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.RateLimitLog;
import com.zclcs.platform.system.api.entity.ao.RateLimitLogAo;
import com.zclcs.platform.system.api.entity.vo.RateLimitLogVo;
import com.zclcs.platform.system.biz.mapper.RateLimitLogMapper;
import com.zclcs.platform.system.biz.service.RateLimitLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 限流拦截日志 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:53.040
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class RateLimitLogServiceImpl extends ServiceImpl<RateLimitLogMapper, RateLimitLog> implements RateLimitLogService {

    @Override
    public BasePage<RateLimitLogVo> findRateLimitLogPage(BasePageAo basePageAo, RateLimitLogVo rateLimitLogVo) {
        BasePage<RateLimitLogVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RateLimitLogVo> queryWrapper = getQueryWrapper(rateLimitLogVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RateLimitLogVo> findRateLimitLogList(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper<RateLimitLogVo> queryWrapper = getQueryWrapper(rateLimitLogVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public RateLimitLogVo findRateLimitLog(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper<RateLimitLogVo> queryWrapper = getQueryWrapper(rateLimitLogVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRateLimitLog(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper<RateLimitLogVo> queryWrapper = getQueryWrapper(rateLimitLogVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<RateLimitLogVo> getQueryWrapper(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper<RateLimitLogVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitLog createRateLimitLog(RateLimitLogAo rateLimitLogAo) {
        RateLimitLog rateLimitLog = new RateLimitLog();
        BeanUtil.copyProperties(rateLimitLogAo, rateLimitLog);
        this.save(rateLimitLog);
        return rateLimitLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitLog updateRateLimitLog(RateLimitLogAo rateLimitLogAo) {
        RateLimitLog rateLimitLog = new RateLimitLog();
        BeanUtil.copyProperties(rateLimitLogAo, rateLimitLog);
        this.updateById(rateLimitLog);
        return rateLimitLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRateLimitLog(List<Long> ids) {
        this.removeByIds(ids);
    }
}
