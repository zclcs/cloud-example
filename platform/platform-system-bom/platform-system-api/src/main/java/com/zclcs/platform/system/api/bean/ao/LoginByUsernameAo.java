package com.zclcs.platform.system.api.bean.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Schema(title = "LoginAo对象", description = "用户")
public class LoginByUsernameAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "用户名")
    private String username;

    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "密码（需加密）加密方式 aes 模式 CFB 填充 NoPadding 偏移量 密钥字符串 在线生成网址：https://oktools.net/aes")
    private String password;


}