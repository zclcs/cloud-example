package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端展示令牌管理
 *
 * @author lengleng
 * @date 2022/6/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "TokenVo对象", description = "token")
public class TokenVo {

    @Schema(description = "tokenId")
    private String id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "终端id")
    private String clientId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "token值")
    private String accessToken;

    @Schema(description = "生成时间")
    private String issuedAt;

    @Schema(description = "过期时间")
    private String expiresAt;

}
