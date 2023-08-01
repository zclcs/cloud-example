package com.zclcs.third.part.database.sync.properties;

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
@ConfigurationProperties(prefix = "third-part-database-sync")
public class ThirdPartDatabaseSyncProperties {

    /**
     * 启动是否生成迁移sql到文件
     */
    private Boolean generateSql;

}
