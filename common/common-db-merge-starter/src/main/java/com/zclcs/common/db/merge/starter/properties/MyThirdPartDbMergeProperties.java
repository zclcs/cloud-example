package com.zclcs.common.db.merge.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zclcs
 */
@ConfigurationProperties(prefix = "my.third.part.db.merge")
public class MyThirdPartDbMergeProperties {

    /**
     * 是否开启数据库脚本合并功能
     */
    private Boolean enable = false;

    /**
     * nacos脚本
     */
    private String nacosSql = "sql/nacos";

    /**
     * xxlJob脚本
     */
    private String xxlJobSql = "sql/xxl-job";

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

    @Override
    public String toString() {
        return "MyDbMergeProperties{" +
                "enable=" + enable +
                ", nacosSql='" + nacosSql + '\'' +
                ", xxlJobSql='" + xxlJobSql + '\'' +
                '}';
    }
}
