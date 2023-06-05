package com.zclcs.platform.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Data
@Component
@ConfigurationProperties(prefix = "platform-system")
@RefreshScope
public class PlatformSystemProperties {

    /**
     * 启动是否生成迁移sql到文件
     */
    private Boolean generateSql;

}
