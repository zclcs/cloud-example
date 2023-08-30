package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
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
 * @since 2023-01-10 10:39:34.182
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long userId;

    /**
     * 用户名
     */
    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String username;

    /**
     * 用户昵称
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String realName;

    /**
     * 密码 不填使用默认密码
     */
    @Size(max = 128, message = "{noMoreThan}")
//    @NotBlank(message = "{required}")
    private String password;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 邮箱
     */
    @Size(max = 128, message = "{noMoreThan}")
    private String email;

    /**
     * 联系电话
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String mobile;

    /**
     * 状态 @@system_user.status 默认有效
     */
    @Size(max = 1, message = "{noMoreThan}")
    @DictValid(value = "system_user.status")
    private String status;

    /**
     * 最近访问时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 性别 @@system_user.gender
     */
    @Size(max = 1, message = "{noMoreThan}")
    @DictValid(value = "system_user.gender")
    private String gender;

    /**
     * 是否开启tab @@yes_no
     */
    @Size(max = 1, message = "{noMoreThan}")
    @DictValid(value = "yes_no")
    private String isTab;

    /**
     * 主题
     */
    @Size(max = 10, message = "{noMoreThan}")
    private String theme;

    /**
     * 头像
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String avatar;

    /**
     * 描述
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String description;

    /**
     * 角色id集合
     */
    private List<Long> roleIds;

    /**
     * 数据权限id集合
     */
    private List<Long> deptIds;


}