package com.zclcs.common.db.merge.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zclcs
 */
@Component
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
     * nacos脚本
     */
    private String nacosTmpSql = "classpath:sql/nacos/sql_tmp/**.sql";

    /**
     * xxlJob脚本
     */
    private String xxlJobSql = "classpath:sql/xxl-job/**.sql";

    /**
     * xxlJob脚本
     */
    private String xxlJobTmpSql = "classpath:sql/xxl-job/sql_tmp/**.sql";

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getNacosSql() {
        return nacosSql;
    }

    public String getNacosTmpSql() {
        return nacosTmpSql;
    }

    public void setNacosTmpSql(String nacosTmpSql) {
        this.nacosTmpSql = nacosTmpSql;
    }

    public String getXxlJobTmpSql() {
        return xxlJobTmpSql;
    }

    public void setXxlJobTmpSql(String xxlJobTmpSql) {
        this.xxlJobTmpSql = xxlJobTmpSql;
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
