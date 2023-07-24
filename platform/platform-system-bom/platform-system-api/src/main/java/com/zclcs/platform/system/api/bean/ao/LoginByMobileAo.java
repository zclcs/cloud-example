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
@Schema(title = "LoginByMobileAo对象", description = "用户")
public class LoginByMobileAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Size(max = 20, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "手机号")
    private String mobile;


}