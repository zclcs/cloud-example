package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.mybatis.flex.utils.PredicateUtil;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.platform.system.api.bean.ao.RateLimitLogAo;
import com.zclcs.platform.system.api.bean.entity.RateLimitLog;
import com.zclcs.platform.system.api.bean.vo.RateLimitLogVo;
import com.zclcs.platform.system.mapper.RateLimitLogMapper;
import com.zclcs.platform.system.service.RateLimitLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.RateLimitLogTableDef.RATE_LIMIT_LOG;

/**
 * 限流拦截日志 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:53.040
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitLogServiceImpl extends ServiceImpl<RateLimitLogMapper, RateLimitLog> implements RateLimitLogService {

    private final Ip2regionSearcher ip2regionSearcher;

    @Override
    public BasePage<RateLimitLogVo> findRateLimitLogPage(BasePageAo basePageAo, RateLimitLogVo rateLimitLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(rateLimitLogVo);
        Page<RateLimitLogVo> rateLimitLogVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, RateLimitLogVo.class);
        return new BasePage<>(rateLimitLogVoPage);
    }

    @Override
    public List<RateLimitLogVo> findRateLimitLogList(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(rateLimitLogVo);
        return this.mapper.selectListByQueryAs(queryWrapper, RateLimitLogVo.class);
    }

    @Override
    public RateLimitLogVo findRateLimitLog(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(rateLimitLogVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, RateLimitLogVo.class);
    }

    @Override
    public Long countRateLimitLog(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(rateLimitLogVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(RateLimitLogVo rateLimitLogVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        RATE_LIMIT_LOG.RATE_LIMIT_LOG_ID,
                        RATE_LIMIT_LOG.RATE_LIMIT_LOG_IP,
                        RATE_LIMIT_LOG.REQUEST_URI,
                        RATE_LIMIT_LOG.REQUEST_METHOD,
                        RATE_LIMIT_LOG.REQUEST_TIME,
                        RATE_LIMIT_LOG.LOCATION,
                        RATE_LIMIT_LOG.CREATE_AT,
                        RATE_LIMIT_LOG.UPDATE_AT
                )
                .where(RATE_LIMIT_LOG.RATE_LIMIT_LOG_IP.likeRight(rateLimitLogVo.getRateLimitLogIp()))
                .and(RATE_LIMIT_LOG.REQUEST_URI.likeRight(rateLimitLogVo.getRequestUri()))
                .and(RATE_LIMIT_LOG.REQUEST_METHOD.likeRight(rateLimitLogVo.getRequestMethod()))
                .and(RATE_LIMIT_LOG.REQUEST_TIME.between(
                        rateLimitLogVo.getRequestTimeFrom(),
                        rateLimitLogVo.getRequestTimeTo(),
                        PredicateUtil.localDateBothNotNull
                ))
                .orderBy(RATE_LIMIT_LOG.REQUEST_TIME.desc())
        ;
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
    public void createRateLimitLogBatch(List<RateLimitLogAo> rateLimitLogAos) {
        List<RateLimitLog> rateLimitLogs = new ArrayList<>();
        for (RateLimitLogAo rateLimitLogAo : rateLimitLogAos) {
            RateLimitLog rateLimitLog = new RateLimitLog();
            BeanUtil.copyProperties(rateLimitLogAo, rateLimitLog);
            setRateLimitLog(rateLimitLog);
            rateLimitLogs.add(rateLimitLog);
        }
        this.saveBatch(rateLimitLogs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRateLimitLog(List<Long> ids) {
        this.removeByIds(ids);
    }

    private void setRateLimitLog(RateLimitLog rateLimitLog) {
        if (StrUtil.isNotBlank(rateLimitLog.getRateLimitLogIp())) {
            rateLimitLog.setLocation(ip2regionSearcher.getAddress(rateLimitLog.getRateLimitLogIp()));
        }
    }
}
