package com.zclcs.data.sync.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zclcs
 */
@ConfigurationProperties(prefix = "my.data.sync")
public class MyDataSyncProperties {

    /**
     * 是否开启数据库脚本合并功能
     */
    private Boolean enable = true;
    /**
     * nacos脚本
     */
    private String nacosSql = "sql/nacos";
    /**
     * xxlJob脚本
     */
    private String xxlJobSql = "sql/xxl-job";
    /**
     * 脚本分隔符
     */
    private String delimiter = "//";

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

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String toString() {
        return "MyDataSyncProperties{" +
                "enable=" + enable +
                ", nacosSql='" + nacosSql + '\'' +
                ", xxlJobSql='" + xxlJobSql + '\'' +
                ", delimiter='" + delimiter + '\'' +
                '}';
    }
}
