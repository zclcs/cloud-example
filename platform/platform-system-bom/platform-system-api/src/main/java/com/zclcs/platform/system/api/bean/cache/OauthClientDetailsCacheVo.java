package com.zclcs.platform.system.api.bean.cache;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 终端信息缓存
 *
 * @author zclcs
 * @since 2023-01-30 16:48:03.522
 */
@Data
public class OauthClientDetailsCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 资源列表
     */
    private String resourceIds;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 域
     */
    private String scope;

    /**
     * 认证类型
     */
    private String authorizedGrantTypes;

    /**
     * 重定向地址
     */
    private String webServerRedirectUri;

    /**
     * 角色列表
     */
    private String authorities;

    /**
     * token 有效期
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    private Integer refreshTokenValidity;

    /**
     * 令牌扩展字段JSON
     */
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    private String autoapprove;


}