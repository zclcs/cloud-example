package com.zclcs.platform.system.api.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.platform.system.api.bean.ao.UserAo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 Entity
 *
 * @author zclcs
 * @since 2023-01-10 10:39:34.182
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_user")
public class User extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id(keyType = KeyType.Auto)
    private Long userId;

    /**
     * 用户名
     */
    @Column("username")
    private String username;

    /**
     * 用户昵称
     */
    @Column("real_name")
    private String realName;

    /**
     * 密码
     */
    @JsonIgnore
    @Column("password")
    private String password;

    /**
     * 部门id
     */
    @Column("dept_id")
    private Long deptId;

    /**
     * 邮箱
     */
    @Column("email")
    private String email;

    /**
     * 联系电话
     */
    @Column("mobile")
    private String mobile;

    /**
     * 状态 @@system_user.status
     */
    @Column("status")
    private String status;

    /**
     * 最近访问时间
     */
    @Column("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 性别 @@system_user.gender
     */
    @Column("gender")
    private String gender;

    /**
     * 是否开启tab @@yes_no
     */
    @Column("is_tab")
    private String isTab;

    /**
     * 主题
     */
    @Column("theme")
    private String theme;

    /**
     * 头像
     */
    @Column("avatar")
    private String avatar;

    /**
     * 描述
     */
    @Column("description")
    private String description;

    public static User convertToUser(UserAo item) {
        if (item == null) {
            return null;
        }
        User result = new User();
        result.setUserId(item.getUserId());
        result.setUsername(item.getUsername());
        result.setRealName(item.getRealName());
        result.setPassword(item.getPassword());
        result.setDeptId(item.getDeptId());
        result.setEmail(item.getEmail());
        result.setMobile(item.getMobile());
        result.setStatus(item.getStatus());
        result.setLastLoginTime(item.getLastLoginTime());
        result.setGender(item.getGender());
        result.setIsTab(item.getIsTab());
        result.setTheme(item.getTheme());
        result.setAvatar(item.getAvatar());
        result.setDescription(item.getDescription());
        return result;
    }


}