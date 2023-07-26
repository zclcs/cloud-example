package com.zclcs.platform.system.api.bean.ao;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户名登录 Ao
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
public class ValidateCodeAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户输入的验证码
     */
    @NotBlank(message = "{required}")
    private String code;

    /**
     * 申请验证码的随机字符串
     */
    @NotBlank(message = "{required}")
    private String randomStr;


}