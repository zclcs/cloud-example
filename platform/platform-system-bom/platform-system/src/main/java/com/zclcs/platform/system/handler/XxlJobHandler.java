package com.zclcs.platform.system.handler;

import com.google.common.base.Stopwatch;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.zclcs.platform.system.service.BlackListService;
import com.zclcs.platform.system.service.RateLimitRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Component
@RequiredArgsConstructor
public class XxlJobHandler {

    private final BlackListService blackListService;
    private final RateLimitRuleService rateLimitRuleService;

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

}
