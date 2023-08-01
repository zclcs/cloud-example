package com.zclcs.third.part.database.sync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zclcs
 */
@Getter
@AllArgsConstructor
public enum Servers {

    PLATFORM_SYSTEM("platform-system", "系统服务"),

    PLATFORM_MAINTENANCE("platform-maintenance", "监控服务"),
    ;

    private final String serverName;
    private final String desc;
}
