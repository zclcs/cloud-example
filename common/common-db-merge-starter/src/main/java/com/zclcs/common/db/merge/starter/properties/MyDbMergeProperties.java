package com.zclcs.common.db.merge.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zclcs
 */
@ConfigurationProperties(prefix = "my.db.merge")
public class MyDbMergeProperties {

    /**
     * 是否开启数据库脚本合并功能
     */
    private Boolean enable = true;
    /**
     * sql脚本
     */
    private String sql = "sql/";
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

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String toString() {
        return "MyDbMergeProperties{" +
                "enable=" + enable +
                ", sql='" + sql + '\'' +
                ", delimiter='" + delimiter + '\'' +
                '}';
    }
}
