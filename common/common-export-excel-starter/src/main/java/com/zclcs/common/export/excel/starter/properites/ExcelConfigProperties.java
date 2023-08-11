package com.zclcs.common.export.excel.starter.properites;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author zclcs
 * @date 2020/3/29
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = ExcelConfigProperties.PREFIX)
public class ExcelConfigProperties {

    static final String PREFIX = "my.excel";

    /**
     * 模板路径
     */
    private String templatePath = "excel";

}
