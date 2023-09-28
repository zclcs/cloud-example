package com.zclcs.common.db.merge.starter.properties;

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
@ConfigurationProperties(prefix = "my.third.part.db.merge")
public class MyThirdPartDbMergeProperties {

    /**
     * 是否开启数据库脚本合并功能
     */
    private Boolean enable = false;

    /**
     * nacos脚本
     */
    private String nacosSql = "classpath:sql/nacos/**.sql";

    /**
     * xxlJob脚本
     */
    private String xxlJobSql = "classpath:sql/xxl-job/**.sql";

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getNacosSql() {
        return nacosSql;
    }

    public void setNacosSql(String nacosSql) {
        this.nacosSql = nacosSql;
    }

    public String getXxlJobSql() {
        return xxlJobSql;
    }

    public void setXxlJobSql(String xxlJobSql) {
        this.xxlJobSql = xxlJobSql;
    }

}
