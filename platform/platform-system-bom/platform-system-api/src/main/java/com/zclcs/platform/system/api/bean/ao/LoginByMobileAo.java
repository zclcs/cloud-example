package com.zclcs.platform.system.api.bean.ao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 手机号登录 Ao
 *
 * @author zclcs
 * @since 2023-01-10 10:39:34.182
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginByMobileAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @Size(max = 20, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String mobile;


}