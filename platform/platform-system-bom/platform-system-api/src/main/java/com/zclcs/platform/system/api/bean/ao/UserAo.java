package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
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
 * @since 2023-09-01 19:55:21.249
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
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long userId;

    /**
     * 用户名
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String username;

    /**
     * 用户昵称
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String realName;

    /**
     * 密码
     * 默认值：默认密码
     */
    @Size(max = 128, message = "{noMoreThan}")
//    @NotBlank(message = "{required}")
    private String password;

    /**
     * 部门id
     * 默认值：0
     */
    @NotNull(message = "{required}")
    private Long deptId;

    /**
     * 邮箱
     * 默认值：
     */
    @Size(max = 128, message = "{noMoreThan}")
    private String email;

    /**
     * 联系电话
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String mobile;

    /**
     * 状态 @@system_user.status
     * 默认值：1
     */
    @DictValid(value = "system_user.status", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String status;

    /**
     * 最近访问时间
     * 默认值：CURRENT_TIMESTAMP
     */
    private LocalDateTime lastLoginTime;

    /**
     * 性别 @@system_user.gender
     * 默认值：
     */
    @DictValid(value = "system_user.gender", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    private String gender;

    /**
     * 是否开启tab @@yes_no
     * 默认值：
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    private String isTab;

    /**
     * 主题
     * 默认值：
     */
    @Size(max = 10, message = "{noMoreThan}")
    private String theme;

    /**
     * 头像
     * 默认值：
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String avatar;

    /**
     * 描述
     * 默认值：
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