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
     * 初始化全量脚本
     */
    private String initSqlLocation = "sql/initial/";
    /**
     * 增量脚本
     */
    private String incrementSqlLocation = "sql/increment/";
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

    public String getInitSqlLocation() {
        return initSqlLocation;
    }

    public void setInitSqlLocation(String initSqlLocation) {
        this.initSqlLocation = initSqlLocation;
    }

    public String getIncrementSqlLocation() {
        return incrementSqlLocation;
    }

    public void setIncrementSqlLocation(String incrementSqlLocation) {
        this.incrementSqlLocation = incrementSqlLocation;
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
                ", initSqlLocation='" + initSqlLocation + '\'' +
                ", incrementSqlLocation='" + incrementSqlLocation + '\'' +
                ", delimiter='" + delimiter + '\'' +
                '}';
    }
}
