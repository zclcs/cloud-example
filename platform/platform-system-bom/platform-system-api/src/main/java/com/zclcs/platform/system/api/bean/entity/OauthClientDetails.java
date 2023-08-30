package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 终端信息 Entity
 *
 * @author zclcs
 * @since 2023-01-30 16:48:03.522
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_oauth_client_details")
public class OauthClientDetails extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户端ID
     */
    @Id(value = "client_id", keyType = KeyType.None)
    private String clientId;

    /**
     * 资源列表
     */
    @Column("resource_ids")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @Column("client_secret")
    private String clientSecret;

    /**
     * 域
     */
    @Column("scope")
    private String scope;

    /**
     * 认证类型
     */
    @Column("authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 重定向地址
     */
    @Column("web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 角色列表
     */
    @Column("authorities")
    private String authorities;

    /**
     * token 有效期
     */
    @Column("access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    @Column("refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 令牌扩展字段JSON
     */
    @Column("additional_information")
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    @Column("autoapprove")
    private String autoapprove;


}