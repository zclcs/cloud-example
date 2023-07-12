package com.zclcs.platform.system.handler;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.Stopwatch;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.zclcs.platform.system.api.bean.entity.BlockLog;
import com.zclcs.platform.system.api.bean.entity.RateLimitLog;
import com.zclcs.platform.system.api.bean.entity.RouteLog;
import com.zclcs.platform.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

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
     * 清理日志表
     */
    @XxlJob("cleanLog")
    public void cleanLog() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Long limit = Long.valueOf(Optional.of(XxlJobHelper.getJobParam()).orElse("0"));
        LocalDateTime localDateTime = DateUtil.beginOfDay(DateUtil.date()).toLocalDateTime();

        Long blockLogCount = blockLogService.lambdaQuery().le(BlockLog::getCreateAt, localDateTime).count();
        while (blockLogCount > limit) {
            blockLogCount -= limit;
            blockLogService.lambdaUpdate().le(BlockLog::getCreateAt, localDateTime).last("limit " + limit).remove();
        }
        blockLogService.lambdaUpdate().le(BlockLog::getCreateAt, localDateTime).remove();

        Long rateLimitLogCount = rateLimitLogService.lambdaQuery().le(RateLimitLog::getCreateAt, localDateTime).count();
        while (rateLimitLogCount > limit) {
            rateLimitLogCount -= limit;
            rateLimitLogService.lambdaUpdate().le(RateLimitLog::getCreateAt, localDateTime).last("limit " + limit).remove();
        }
        rateLimitLogService.lambdaUpdate().le(RateLimitLog::getCreateAt, localDateTime).remove();

        Long routeLogCount = routeLogService.lambdaQuery().le(RouteLog::getCreateAt, localDateTime).count();
        while (routeLogCount > limit) {
            routeLogCount -= limit;
            routeLogService.lambdaUpdate().le(RouteLog::getCreateAt, localDateTime).last("limit " + limit).remove();
        }
        routeLogService.lambdaUpdate().le(RouteLog::getCreateAt, localDateTime).remove();

        XxlJobHelper.log("Clean Log Completed - {}", stopwatch.stop());
    }

}
