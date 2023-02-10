package com.zclcs.platform.system.api.fegin;

import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.core.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteRateLimitRuleService", value = ServiceNameConstant.PLATFORM_SYSTEM_SERVICE)
public interface RemoteRateLimitRuleService {

    /**
     * 刷新限流规则缓存
     *
     * @return BaseRsp
     */
    @GetMapping(value = "/rateLimitRule/refresh", headers = SecurityConstant.HEADER_FROM_IN)
    BaseRsp<Object> refresh();

}
