package com.zclcs.platform.system.api.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:55:21.249
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     * 默认值：
     */
    private Long userId;

    /**
     * 用户名
     * 默认值：
     */
    private String username;

    /**
     * 用户昵称
     * 默认值：
     */
    private String realName;

    /**
     * 密码
     * 默认值：
     */
    @JsonIgnore
    private String password;

    /**
     * 部门id
     * 默认值：0
     */
    private Long deptId;

    /**
     * 邮箱
     * 默认值：
     */
    private String email;

    /**
     * 联系电话
     * 默认值：
     */
    private String mobile;

    /**
     * 状态 @@system_user.status
     * 默认值：1
     */
    private String status;

    /**
     * 状态 @@system_user.status
     */
    private String statusText;

    public String getStatusText() {
        return DictCacheUtil.getDictTitle("system_user.status", this.status);
    }

    /**
     * 最近访问时间
     * 默认值：CURRENT_TIMESTAMP
     */
    private LocalDateTime lastLoginTime;

    /**
     * 性别 @@system_user.gender
     * 默认值：
     */
    private String gender;

    /**
     * 性别 @@system_user.gender
     */
    private String genderText;

    public String getGenderText() {
        return DictCacheUtil.getDictTitle("system_user.gender", this.gender);
    }

    /**
     * 是否开启tab @@yes_no
     * 默认值：
     */
    private String isTab;

    /**
     * 是否开启tab @@yes_no
     */
    private String isTabText;

    public String getIsTabText() {
        return DictCacheUtil.getDictTitle("yes_no", this.isTab);
    }

    /**
     * 主题
     * 默认值：
     */
    private String theme;

    /**
     * 头像
     * 默认值：
     */
    private String avatar;

    /**
     * 描述
     * 默认值：
     */
    private String description;

    /**
     * 角色id集合
     */
    private List<Long> roleIds;

    /**
     * 数据权限id集合
     */
    private List<Long> deptIds;

    /**
     * 用户角色名称集合
     */
    private List<String> roleNames;

    /**
     * 用户角色名称集合String
     */
    private String roleNameString;

    /**
     * 用户权限集合
     */
    private List<String> permissions;


}