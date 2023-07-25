package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
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
public class OauthClientDetailsAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户端ID
     */
    @NotBlank(message = "{required}", groups = UpdateStrategy.class)
    @Size(max = 64, message = "{noMoreThan}")
    private String clientId;

    /**
     * 资源列表
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String clientSecret;

    /**
     * 域
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String scope;

    /**
     * 认证类型
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String authorizedGrantTypes;

    /**
     * 重定向地址
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String webServerRedirectUri;

    /**
     * 角色列表
     */
    @Size(max = 256, message = "{noMoreThan}")
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
    @Size(max = 4096, message = "{noMoreThan}")
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String autoapprove;

    /**
     * 菜单id集合
     */
    private List<Long> menuIds;


}