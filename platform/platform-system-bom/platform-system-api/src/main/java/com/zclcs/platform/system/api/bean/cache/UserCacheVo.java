package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:55:21.249
 */
@Data
public class UserCacheVo implements Serializable {

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
    private Long deptId;

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
     * 状态 @@system_user.status
     */
    private String statusText;

    public String getStatusText() {
        return DictCacheUtil.getDictTitle("system_user.status", this.status);
    }

    /**
     * 最近访问时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 性别 @@system_user.gender
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


}