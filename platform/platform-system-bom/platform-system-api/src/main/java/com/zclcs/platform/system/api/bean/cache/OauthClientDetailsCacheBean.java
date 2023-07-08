package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.platform.system.api.bean.entity.OauthClientDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 终端信息 Entity
 *
 * @author zclcs
 * @date 2023-01-30 16:48:03.522
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "OauthClientDetails缓存对象", description = "终端信息缓存")
public class OauthClientDetailsCacheBean implements Serializable {

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

    public static OauthClientDetailsCacheBean convertToOauthClientDetailsCacheBean(OauthClientDetails item) {
        if (item == null) {
            return null;
        }
        OauthClientDetailsCacheBean result = new OauthClientDetailsCacheBean();
        result.setClientId(item.getClientId());
        result.setResourceIds(item.getResourceIds());
        result.setClientSecret(item.getClientSecret());
        result.setScope(item.getScope());
        result.setAuthorizedGrantTypes(item.getAuthorizedGrantTypes());
        result.setWebServerRedirectUri(item.getWebServerRedirectUri());
        result.setAuthorities(item.getAuthorities());
        result.setAccessTokenValidity(item.getAccessTokenValidity());
        result.setRefreshTokenValidity(item.getRefreshTokenValidity());
        result.setAdditionalInformation(item.getAdditionalInformation());
        result.setAutoapprove(item.getAutoapprove());
        return result;
    }


}