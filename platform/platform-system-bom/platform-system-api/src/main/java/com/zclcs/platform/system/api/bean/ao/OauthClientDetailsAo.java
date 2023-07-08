package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 终端信息 Ao
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
@Schema(title = "OauthClientDetailsAo对象", description = "终端信息")
public class OauthClientDetailsAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{required}", groups = UpdateStrategy.class)
    @Size(max = 64, message = "{noMoreThan}")
    @Schema(description = "客户端ID")
    private String clientId;

    @Size(max = 256, message = "{noMoreThan}")
    @Schema(description = "资源列表")
    private String resourceIds;

    @Size(max = 256, message = "{noMoreThan}")
    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Size(max = 256, message = "{noMoreThan}")
    @Schema(description = "域")
    private String scope;

    @Size(max = 256, message = "{noMoreThan}")
    @Schema(description = "认证类型")
    private String authorizedGrantTypes;

    @Size(max = 256, message = "{noMoreThan}")
    @Schema(description = "重定向地址")
    private String webServerRedirectUri;

    @Size(max = 256, message = "{noMoreThan}")
    @Schema(description = "角色列表")
    private String authorities;

    @Schema(description = "token 有效期")
    private Integer accessTokenValidity;

    @Schema(description = "刷新令牌有效期")
    private Integer refreshTokenValidity;

    @Size(max = 4096, message = "{noMoreThan}")
    @Schema(description = "令牌扩展字段JSON")
    private String additionalInformation;

    @Size(max = 256, message = "{noMoreThan}")
    @Schema(description = "是否自动放行")
    private String autoapprove;

    @Schema(description = "菜单id集合")
    private List<Long> menuIds;


}