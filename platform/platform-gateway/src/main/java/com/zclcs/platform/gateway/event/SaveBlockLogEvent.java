package com.zclcs.platform.gateway.event;

import com.zclcs.platform.system.api.bean.ao.BlockLogAo;
import org.springframework.context.ApplicationEvent;

/**
 * 保存网关日志事件
 *
 * @author zclcs
 */
public class SaveBlockLogEvent extends ApplicationEvent {
    public SaveBlockLogEvent(final BlockLogAo blockLogAo) {
        super(blockLogAo);
    }

}
