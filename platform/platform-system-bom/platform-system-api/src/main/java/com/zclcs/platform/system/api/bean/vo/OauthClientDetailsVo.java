package com.zclcs.platform.system.api.bean.vo;

import com.mybatisflex.annotation.Column;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 终端信息 Vo
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
public class OauthClientDetailsVo extends BaseEntity implements Serializable {

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

    /**
     * 菜单id集合
     */
    @Column(ignore = true)
    private List<Long> menuIds;


}