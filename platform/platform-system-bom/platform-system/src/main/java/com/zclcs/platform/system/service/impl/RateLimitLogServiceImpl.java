package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.utils.AddressUtil;
import com.zclcs.common.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.entity.RateLimitLog;
import com.zclcs.platform.system.api.entity.ao.RateLimitLogAo;
import com.zclcs.platform.system.api.entity.vo.RateLimitLogVo;
import com.zclcs.platform.system.mapper.RateLimitLogMapper;
import com.zclcs.platform.system.service.RateLimitLogService;
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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RateLimitLogServiceImpl extends ServiceImpl<RateLimitLogMapper, RateLimitLog> implements RateLimitLogService {

    @Override
    public BasePage<RateLimitLogVo> findRateLimitLogPage(BasePageAo basePageAo, RateLimitLogVo rateLimitLogVo) {
        BasePage<RateLimitLogVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RateLimitLogVo> queryWrapper = getQueryWrapper(rateLimitLogVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RateLimitLogVo> findRateLimitLogList(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper<RateLimitLogVo> queryWrapper = getQueryWrapper(rateLimitLogVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public RateLimitLogVo findRateLimitLog(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper<RateLimitLogVo> queryWrapper = getQueryWrapper(rateLimitLogVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRateLimitLog(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper<RateLimitLogVo> queryWrapper = getQueryWrapper(rateLimitLogVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<RateLimitLogVo> getQueryWrapper(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper<RateLimitLogVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "srll.rate_limit_log_ip", rateLimitLogVo.getRateLimitLogIp());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "srll.request_uri", rateLimitLogVo.getRequestUri());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "srll.request_method", rateLimitLogVo.getRequestMethod());
        QueryWrapperUtil.betweenDateAddTimeNotBlank(queryWrapper, "srll.create_at", rateLimitLogVo.getCreateTimeFrom(), rateLimitLogVo.getCreateTimeTo());
        queryWrapper.orderByDesc("srll.create_at");
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RateLimitLog createRateLimitLog(RateLimitLogAo rateLimitLogAo) {
        RateLimitLog rateLimitLog = new RateLimitLog();
        BeanUtil.copyProperties(rateLimitLogAo, rateLimitLog);
        setRateLimitLog(rateLimitLog);
        this.save(rateLimitLog);
        return rateLimitLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRateLimitLog(List<Long> ids) {
        this.removeByIds(ids);
    }

    private void setRateLimitLog(RateLimitLog rateLimitLog) {
        if (StrUtil.isNotBlank(rateLimitLog.getRateLimitLogIp())) {
            rateLimitLog.setLocation(AddressUtil.getCityInfo(rateLimitLog.getRateLimitLogIp()));
        }
    }
}
