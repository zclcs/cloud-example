package com.zclcs.platform.system.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author zclcs
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = "platform-system")
public class PlatformSystemProperties {

    /**
     * 启动是否生成迁移sql到文件
     */
    private Boolean generateSql;
}
