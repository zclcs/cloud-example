package com.zclcs.platform.system.handler;

import cn.dev33.satoken.same.SaSameUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.base.Stopwatch;
import com.mybatisflex.core.query.QueryWrapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.zclcs.platform.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.zclcs.platform.system.api.bean.entity.table.BlockLogTableDef.BLOCK_LOG;
import static com.zclcs.platform.system.api.bean.entity.table.RateLimitLogTableDef.RATE_LIMIT_LOG;
import static com.zclcs.platform.system.api.bean.entity.table.RouteLogTableDef.ROUTE_LOG;

/**
 * @author zclcs
 */
@Component
@RequiredArgsConstructor
public class XxlJobHandler {

    private final BlackListService blackListService;
    private final RateLimitRuleService rateLimitRuleService;
    private final BlockLogService blockLogService;
    private final RateLimitLogService rateLimitLogService;
    private final RouteLogService routeLogService;

    /**
     * 刷新限流配置和黑名单配置
     */
    @XxlJob("refreshBlackListAndRateLimitRules")
    public void refreshBlackListAndRateLimitRules() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        blackListService.cacheAllBlackList();
        rateLimitRuleService.cacheAllRateLimitRules();
        XxlJobHelper.log("Cache BlackList And RateLimitRules Completed - {}", stopwatch.stop());
    }

    /**
     * 刷新sa-same-token
     */
    @XxlJob("refreshSaSameToken")
    public void refreshSaSameToken() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        SaSameUtil.refreshToken();
        XxlJobHelper.log("Refresh Sa Same Token Completed - {}", stopwatch.stop());
    }

    /**
     * 清理日志表
     */
    @XxlJob("cleanLog")
    public void cleanLog() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Long limit = Long.valueOf(Optional.of(XxlJobHelper.getJobParam()).orElse("0"));
        LocalDateTime localDateTime = DateUtil.beginOfDay(DateUtil.date()).toLocalDateTime();

        Long blockLogCount = blockLogService.count(new QueryWrapper().where(BLOCK_LOG.CREATE_AT.le(localDateTime)));
        while (blockLogCount > limit) {
            blockLogCount -= limit;
            blockLogService.remove(new QueryWrapper().where(BLOCK_LOG.CREATE_AT.le(localDateTime)).limit(limit));
        }
        blockLogService.remove(new QueryWrapper().where(BLOCK_LOG.CREATE_AT.le(localDateTime)));

        Long rateLimitLogCount = rateLimitLogService.count(new QueryWrapper().where(RATE_LIMIT_LOG.CREATE_AT.le(localDateTime)));
        while (rateLimitLogCount > limit) {
            rateLimitLogCount -= limit;
            rateLimitLogService.remove(new QueryWrapper().where(RATE_LIMIT_LOG.CREATE_AT.le(localDateTime)).limit(limit));
        }
        rateLimitLogService.remove(new QueryWrapper().where(RATE_LIMIT_LOG.CREATE_AT.le(localDateTime)));

        Long routeLogCount = routeLogService.count(new QueryWrapper().where(ROUTE_LOG.CREATE_AT.le(localDateTime)));
        while (routeLogCount > limit) {
            routeLogCount -= limit;
            routeLogService.remove(new QueryWrapper().where(ROUTE_LOG.CREATE_AT.le(localDateTime)).limit(limit));
        }
        routeLogService.remove(new QueryWrapper().where(ROUTE_LOG.CREATE_AT.le(localDateTime)));

        XxlJobHelper.log("Clean Log Completed - {}", stopwatch.stop());
    }

}
