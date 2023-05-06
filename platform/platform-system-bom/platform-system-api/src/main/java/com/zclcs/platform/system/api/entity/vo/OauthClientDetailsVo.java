package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
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
@Schema(title = "OauthClientDetailsVo对象", description = "终端信息")
public class OauthClientDetailsVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "资源列表")
    private String resourceIds;

    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Schema(description = "域")
    private String scope;

    @Schema(description = "认证类型")
    private String authorizedGrantTypes;

    @Schema(description = "重定向地址")
    private String webServerRedirectUri;

    @Schema(description = "角色列表")
    private String authorities;

    @Schema(description = "token 有效期")
    private Integer accessTokenValidity;

    @Schema(description = "刷新令牌有效期")
    private Integer refreshTokenValidity;

    @Schema(description = "令牌扩展字段JSON")
    private String additionalInformation;

    @Schema(description = "是否自动放行")
    private String autoapprove;

    @Schema(description = "菜单id集合")
    private List<Long> menuIds;


}