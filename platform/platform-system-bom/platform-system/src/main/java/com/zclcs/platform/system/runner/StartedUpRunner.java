package com.zclcs.platform.system.runner;

import com.google.common.base.Stopwatch;
import com.zclcs.cloud.lib.core.utils.BaseUtil;
import com.zclcs.platform.system.service.BlackListService;
import com.zclcs.platform.system.service.RateLimitRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final Environment environment;
    private final BlackListService blackListService;
    private final RateLimitRuleService rateLimitRuleService;

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            BaseUtil.printSystemUpBanner(environment);
            Stopwatch stopwatch = Stopwatch.createStarted();
            blackListService.cacheAllBlackList();
            rateLimitRuleService.cacheAllRateLimitRules();
            log.info("Cache BlackList And RateLimitRules Completed - {}", stopwatch.stop());
        }
    }
}
