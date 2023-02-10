package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.common.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 终端信息 Entity
 *
 * @author zclcs
 * @date 2023-01-30 16:48:03.522
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_oauth_client_details")
@Schema(name = "OauthClientDetails对象", description = "终端信息")
public class OauthClientDetails extends BaseEntity {

    /**
     * 客户端ID
     */
    @TableId(value = "client_id")
    private String clientId;

    /**
     * 资源列表
     */
    @TableField("resource_ids")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @TableField("client_secret")
    private String clientSecret;

    /**
     * 域
     */
    @TableField("scope")
    private String scope;

    /**
     * 认证类型
     */
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 重定向地址
     */
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 角色列表
     */
    @TableField("authorities")
    private String authorities;

    /**
     * token 有效期
     */
    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 令牌扩展字段JSON
     */
    @TableField("additional_information")
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    @TableField("autoapprove")
    private String autoapprove;


}