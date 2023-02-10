package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 终端信息 Vo
 *
 * @author zclcs
 * @date 2023-01-30 16:48:03.522
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "OauthClientDetailsVo对象", description = "终端信息")
public class OauthClientDetailsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "客户端ID")
    private String clientId;

    @Schema(name = "资源列表")
    private String resourceIds;

    @Schema(name = "客户端密钥")
    private String clientSecret;

    @Schema(name = "域")
    private String scope;

    @Schema(name = "认证类型")
    private String authorizedGrantTypes;

    @Schema(name = "重定向地址")
    private String webServerRedirectUri;

    @Schema(name = "角色列表")
    private String authorities;

    @Schema(name = "token 有效期")
    private Integer accessTokenValidity;

    @Schema(name = "刷新令牌有效期")
    private Integer refreshTokenValidity;

    @Schema(name = "令牌扩展字段JSON")
    private String additionalInformation;

    @Schema(name = "是否自动放行")
    private String autoapprove;

    @Schema(name = "菜单id集合")
    private List<Long> menuIds;


}