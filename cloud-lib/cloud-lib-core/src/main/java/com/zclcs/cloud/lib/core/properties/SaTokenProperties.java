package com.zclcs.cloud.lib.core.properties;

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
@ConfigurationProperties(prefix = "sa-token")
public class SaTokenProperties {

    /**
     * 服务缓存前缀
     */
    private Long refreshTokenTimeOut = 608400L;

    /**
     * 服务缓存前缀
     */
    private String tokenName = "Authorization";
}
