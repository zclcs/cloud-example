package com.zclcs.cloud.lib.logging.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;

import java.util.Map;

/**
 * @author zclcs
 */
@Slf4j
public class LoggingLevelHandler {
    private LoggingSystem loggingSystem;

    public LoggingLevelHandler(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }

    /**
     * 设置日志级别
     *
     * @param loggerName 日志包名
     * @param level      日志级别
     */
    private void levelConfig(String loggerName, String level) {
        LogLevel logLevel = LogLevel.valueOf(level.toUpperCase());
        loggingSystem.setLogLevel(loggerName, logLevel);
    }

    /**
     * 更新日志级别
     *
     * @param configStr 配置字符串
     */
    public void configLoggingLevel(String configStr) {
        // 无效字符串不处理
        if (!StrUtil.hasLetter(configStr)) {
            log.error("invalid string for logging");
            return;
        }
        TypeReference<Map<String, String>> valueTypeRef = new TypeReference<>() {
        };
        Map<String, String> map = JsonUtil.readValue(configStr, valueTypeRef);
        if (CollectionUtil.isNotEmpty(map)) {
            for (Map.Entry<String, String> objectObjectEntry : map.entrySet()) {
                String loggerName = objectObjectEntry.getKey();
                String level = objectObjectEntry.getValue();
                if (StrUtil.isNotBlank(level)) {
                    log.debug("日志级别变更 {} ：{}", loggerName, level);
                    levelConfig(loggerName, level);
                }
            }
        }
    }
}
