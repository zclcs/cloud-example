package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import jakarta.validation.constraints.NotNull;
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
 * @since 2023-09-01 19:54:03.427
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
     * 默认值：
     */
    @Size(max = 64, message = "{noMoreThan}")
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private String clientId;

    /**
     * 资源列表
     * 默认值：
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String resourceIds;

    /**
     * 客户端密钥
     * 默认值：
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String clientSecret;

    /**
     * 域
     * 默认值：
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String scope;

    /**
     * 认证类型
     * 默认值：
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String authorizedGrantTypes;

    /**
     * 重定向地址
     * 默认值：
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String webServerRedirectUri;

    /**
     * 角色列表
     * 默认值：
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String authorities;

    /**
     * token 有效期
     * 默认值：86400
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     * 默认值：86400
     */
    private Integer refreshTokenValidity;

    /**
     * 令牌扩展字段JSON
     * 默认值：
     */
    @Size(max = 4096, message = "{noMoreThan}")
    private String additionalInformation;

    /**
     * 是否自动放行
     * 默认值：true
     */
    @Size(max = 256, message = "{noMoreThan}")
    private String autoapprove;

    /**
     * 菜单id集合
     */
    private List<Long> menuIds;


}