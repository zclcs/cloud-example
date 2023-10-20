package com.zclcs.cloud.lib.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zclcs
 */
@Getter
@AllArgsConstructor
public enum ServerName {

    /**
     * 系统服务
     */
    PLATFORM_SYSTEM("platform-system"),

    /**
     * 监控服务
     */
    PLATFORM_MAINTENANCE("platform-maintenance"),

    /**
     * 网关服务
     */
    PLATFORM_GATEWAY("platform-gateway"),

    /**
     * 测试服务
     */
    TEST_TEST("test-test"),
    ;

    private final String serverName;
}
