package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 Ao
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
@Schema(name = "UserAo对象", description = "用户")
public class UserAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "用户id")
    private Long userId;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "用户名", required = true)
    private String username;

    @Size(max = 128, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "密码", required = true)
    private String password;

    @Schema(description = "部门id")
    private Long deptId;

    @Size(max = 128, message = "{noMoreThan}")
    @Schema(description = "邮箱")
    private String email;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(description = "联系电话")
    private String mobile;

    @Size(max = 1, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "状态 0锁定 1有效", required = true)
    private String status;

    @Schema(description = "最近访问时间")
    private LocalDateTime lastLoginTime;

    @Size(max = 1, message = "{noMoreThan}")
    @Schema(description = "性别 0男 1女 2保密")
    private String gender;

    @Size(max = 1, message = "{noMoreThan}")
    @Schema(description = "是否开启tab，0关闭 1开启")
    private String isTab;

    @Size(max = 10, message = "{noMoreThan}")
    @Schema(description = "主题")
    private String theme;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(description = "头像")
    private String avatar;

    @Size(max = 100, message = "{noMoreThan}")
    @Schema(description = "描述")
    private String description;


}