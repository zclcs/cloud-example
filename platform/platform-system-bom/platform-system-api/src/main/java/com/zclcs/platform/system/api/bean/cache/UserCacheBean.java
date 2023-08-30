package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.platform.system.api.bean.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户缓存
 *
 * @author zclcs
 * @since 2023-01-10 10:39:34.182
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserCacheBean implements Serializable {

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
    private String password;

    /**
     * 部门id
     */
    private String deptId;

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
    private String status;

    /**
     * 性别 @@system_user.gender
     */
    private String gender;

    /**
     * 是否开启tab @@yes_no
     */
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

    public static UserCacheBean convertToUserCacheBean(User item) {
        if (item == null) {
            return null;
        }
        UserCacheBean result = new UserCacheBean();
        result.setUserId(item.getUserId());
        result.setUsername(item.getUsername());
        result.setRealName(item.getRealName());
        result.setPassword(item.getPassword());
        result.setDeptId(item.getDeptId());
        result.setEmail(item.getEmail());
        result.setMobile(item.getMobile());
        result.setStatus(item.getStatus());
        result.setGender(item.getGender());
        result.setIsTab(item.getIsTab());
        result.setTheme(item.getTheme());
        result.setAvatar(item.getAvatar());
        result.setDescription(item.getDescription());
        return result;
    }


}