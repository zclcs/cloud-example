package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@Schema(title = "UserAo对象", description = "用户")
public class UserAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "用户id")
    private Long userId;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String realName;

    @Size(max = 128, message = "{noMoreThan}")
//    @NotBlank(message = "{required}")
    @Schema(description = "密码 不填使用默认密码")
    private String password;

    @Schema(description = "部门id")
    private String deptId;

    @Size(max = 128, message = "{noMoreThan}")
    @Schema(description = "邮箱")
    private String email;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(description = "联系电话")
    private String mobile;

    @Size(max = 1, message = "{noMoreThan}")
    @Schema(description = "状态 @@system_user.status 默认有效")
    @DictValid(value = "system_user.status")
    private String status;

    @Schema(description = "最近访问时间")
    private LocalDateTime lastLoginTime;

    @Size(max = 1, message = "{noMoreThan}")
    @Schema(description = "性别 @@system_user.gender")
    @DictValid(value = "system_user.gender")
    private String gender;

    @Size(max = 1, message = "{noMoreThan}")
    @Schema(description = "是否开启tab @@yes_no")
    @DictValid(value = "yes_no")
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

    @Schema(description = "角色id集合")
    private List<Long> roleIds;

    @Schema(description = "数据权限id集合")
    private List<Long> deptIds;


}