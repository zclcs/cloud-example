package com.zclcs.cloud.lib.core.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author zclcs
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = "my")
public class GlobalProperties {

    /**
     * 服务缓存前缀
     */
    private String redisCachePrefix;

    /**
     * 默认密码
     */
    private String defaultPassword;

    /**
     * 允许的文件类型 {@link cn.hutool.core.io.FileTypeUtil}
     */
    private List<String> allowFileType;

}
