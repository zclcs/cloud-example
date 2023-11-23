package com.zclcs.cloud.lib.security.lite.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zclcs
 * 资源服务器对外直接暴露URL,如果设置contex-path 要特殊处理
 */
@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = "my.security")
public class MySecurityProperties {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private List<String> ignoreUrls = new ArrayList<>();

}
