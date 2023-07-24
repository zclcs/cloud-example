package com.zclcs.platform.system.api.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "LoginVo对象", description = "用户")
public class UserTokenVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户token")
    private String token;

    @Schema(description = "用户token")
    private LoginVo userinfo;

    @Schema(description = "当前会话 token 剩余有效时间（单位: 秒，返回 -1 代表永久有效）")
    private Long expire;


}