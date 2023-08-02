package com.zclcs.platform.gateway.event;

import com.zclcs.platform.system.api.bean.ao.RouteLogAo;
import org.springframework.context.ApplicationEvent;

/**
 * 保存网关日志事件
 *
 * @author zclcs
 */
public class SaveRouteLogEvent extends ApplicationEvent {
    public SaveRouteLogEvent(final RouteLogAo routeLogAo) {
        super(routeLogAo);
    }

}
