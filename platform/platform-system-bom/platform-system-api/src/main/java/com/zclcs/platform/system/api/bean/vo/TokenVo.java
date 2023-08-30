package com.zclcs.platform.system.api.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端展示令牌管理
 *
 * @author zclcs
 * @since 2022/6/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenVo {

    /**
     * tokenId
     */
    private String id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 终端id
     */
    private String clientId;

    /**
     * 用户名
     */
    private String username;

    /**
     * token值
     */
    private String accessToken;

    /**
     * 生成时间
     */
    private String issuedAt;

    /**
     * 过期时间
     */
    private String expiresAt;

}
