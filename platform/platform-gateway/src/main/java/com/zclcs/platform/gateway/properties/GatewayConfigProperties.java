package com.zclcs.platform.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author zclcs
 * <p>
 * 网关配置文件
 */
@Setter
@Getter
@RefreshScope
@ConfigurationProperties("gateway")
public class GatewayConfigProperties {

    /**
     * 网关解密登录前端密码 秘钥
     */
    private String encodeKey;

    /**
     * 网关是否需要解密前端密码
     */
    private Boolean isDecodePassword = true;

    /**
     * 网关是否需要校验验证码
     */
    private Boolean isCheckValidCode = true;

    /**
     * 网关需要校验验证码的路径 支持通配符
     */
    private List<String> needCheckValidCodeUrls;

    /**
     * 网关白名单 支持通配符
     */
    private List<String> ignoreUrls;

    @Override
    public String toString() {
        return "GatewayConfigProperties{" +
                "encodeKey='" + encodeKey + '\'' +
                ", isDecodePassword=" + isDecodePassword +
                ", isCheckValidCode=" + isCheckValidCode +
                ", needCheckValidCodeUrls=" + needCheckValidCodeUrls +
                ", ignoreUrls=" + ignoreUrls +
                '}';
    }
}
