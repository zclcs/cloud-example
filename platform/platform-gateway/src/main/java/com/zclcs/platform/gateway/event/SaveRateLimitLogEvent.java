package com.zclcs.platform.gateway.event;

import com.zclcs.platform.system.api.bean.ao.RateLimitLogAo;
import org.springframework.context.ApplicationEvent;

/**
 * 保存网关日志事件
 *
 * @author zclcs
 */
public class SaveRateLimitLogEvent extends ApplicationEvent {
    public SaveRateLimitLogEvent(final RateLimitLogAo rateLimitLogAo) {
        super(rateLimitLogAo);
    }

}
