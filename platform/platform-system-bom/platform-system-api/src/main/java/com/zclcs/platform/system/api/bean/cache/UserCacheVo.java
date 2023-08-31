package com.zclcs.platform.system.api.bean.cache;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户缓存
 *
 * @author zclcs
 * @since 2023-01-10 10:39:34.182
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


}