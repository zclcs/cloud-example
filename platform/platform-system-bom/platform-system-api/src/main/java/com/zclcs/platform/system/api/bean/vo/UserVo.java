package com.zclcs.platform.system.api.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import com.zclcs.platform.system.api.bean.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户 Vo
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
public class UserVo extends BaseEntity implements Serializable {

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

    /**
     * 角色id集合
     */
    private transient List<Long> roleIds;

    /**
     * 数据权限id集合
     */
    private transient List<Long> deptIds;

    /**
     * 用户角色名称集合
     */
    private transient List<String> roleNames;

    /**
     * 用户角色名称集合String
     */
    private transient String roleNameString;

    /**
     * 用户权限集合
     */
    private transient List<String> permissions;

    public static UserVo convertToUserVo(User item) {
        if (item == null) {
            return null;
        }
        UserVo result = new UserVo();
        result.setUserId(item.getUserId());
        result.setUsername(item.getUsername());
        result.setRealName(item.getRealName());
        result.setPassword(item.getPassword());
        result.setEmail(item.getEmail());
        result.setMobile(item.getMobile());
        result.setStatus(item.getStatus());
        result.setLastLoginTime(item.getLastLoginTime());
        result.setGender(item.getGender());
        result.setIsTab(item.getIsTab());
        result.setTheme(item.getTheme());
        result.setAvatar(item.getAvatar());
        result.setDescription(item.getDescription());
        result.setDeptId(item.getDeptId());
        result.setVersion(item.getVersion());
        result.setTenantId(item.getTenantId());
        result.setCreateAt(item.getCreateAt());
        result.setCreateBy(item.getCreateBy());
        result.setUpdateAt(item.getUpdateAt());
        result.setUpdateBy(item.getUpdateBy());
        return result;
    }

    public static List<UserVo> convertToList(List<User> items) {
        if (items == null) {
            return null;
        }
        List<UserVo> result = new ArrayList<>();
        for (User item : items) {
            if (item != null) {
                result.add(convertToUserVo(item));
            }
        }
        return result;
    }


}