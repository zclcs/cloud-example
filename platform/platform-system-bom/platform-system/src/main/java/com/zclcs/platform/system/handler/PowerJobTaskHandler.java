package com.zclcs.platform.system.handler;

import cn.dev33.satoken.same.SaSameUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.base.Stopwatch;
import com.mybatisflex.core.query.QueryWrapper;
import com.zclcs.platform.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.annotation.PowerJobHandler;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.log.OmsLogger;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.zclcs.platform.system.api.bean.entity.table.BlockLogTableDef.BLOCK_LOG;
import static com.zclcs.platform.system.api.bean.entity.table.RateLimitLogTableDef.RATE_LIMIT_LOG;
import static com.zclcs.platform.system.api.bean.entity.table.RouteLogTableDef.ROUTE_LOG;

/**
 * @author zclcs
 */
@Component("powerJobTaskHandler")
@RequiredArgsConstructor
public class PowerJobTaskHandler {

    private final BlackListService blackListService;
    private final RateLimitRuleService rateLimitRuleService;
    private final BlockLogService blockLogService;
    private final RateLimitLogService rateLimitLogService;
    private final RouteLogService routeLogService;

    /**
     * 刷新限流配置和黑名单配置
     * 处理器配置方法1： 全限定类名#方法名，比如 tech.powerjob.samples.tester.SpringMethodProcessorService#testEmptyReturn
     * 处理器配置方法2： SpringBean名称#方法名，比如 springMethodProcessorService#testEmptyReturn
     *
     * @param context 必须要有入参 TaskContext，返回值可以是 null，也可以是其他任意类型。正常返回代表成功，抛出异常代表执行失败
     */
    @PowerJobHandler(name = "refreshBlackListAndRateLimitRules")
    public void refreshBlackListAndRateLimitRules(TaskContext context) throws Exception {
        OmsLogger omsLogger = context.getOmsLogger();
        Stopwatch stopwatch = Stopwatch.createStarted();
        blackListService.cacheAllBlackList();
        rateLimitRuleService.cacheAllRateLimitRules();
        omsLogger.info("Cache BlackList And RateLimitRules Completed - {}", stopwatch.stop());
    }

    /**
     * 刷新sa-same-token
     */
    @PowerJobHandler(name = "refreshSaSameToken")
    public void refreshSaSameToken(TaskContext context) throws Exception {
        OmsLogger omsLogger = context.getOmsLogger();
        Stopwatch stopwatch = Stopwatch.createStarted();
        SaSameUtil.refreshToken();
        omsLogger.info("Refresh Sa Same Token Completed - {}", stopwatch.stop());
    }

    /**
     * 清理日志表
     */
    @PowerJobHandler(name = "cleanLog")
    public void cleanLog(TaskContext context) throws Exception {
        OmsLogger omsLogger = context.getOmsLogger();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Long limit = Long.valueOf(Optional.of(context.getJobParams()).orElse("1000"));
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

        omsLogger.info("Clean Log Completed - {}", stopwatch.stop());
    }

}
