package com.zclcs.platform.system.api.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
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
 * 登录 Vo
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
public class LoginVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String realName;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 状态 @@system_user.status
     */
    @DictText(value = "system_user.status")
    private String status;

    /**
     * 最近访问时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 性别 @@system_user.gender
     */
    @DictText(value = "system_user.gender")
    private String gender;

    /**
     * 是否开启tab @@yes_no
     */
    @DictText(value = "yes_no")
    private String isTab;

    /**
     * 主题
     */
    private String theme;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    private String description;

    /**
     * 部门id
     */
    private Long deptId;


}