package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:55:21.249
 */
@Data
public class UserExcelVo {

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称")
    private String realName;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String mobile;

    /**
     * 状态 @@system_user.status
     */
    @ExcelProperty(value = "状态 @@system_user.status")
    private String status;

    public void setStatus(String status) {
        this.status = DictCacheUtil.getDictTitle("system_user.status", status);
    }

    /**
     * 最近访问时间
     */
    @ExcelProperty(value = "最近访问时间")
    private LocalDateTime lastLoginTime;

    /**
     * 性别 @@system_user.gender
     */
    @ExcelProperty(value = "性别 @@system_user.gender")
    private String gender;

    public void setGender(String gender) {
        this.gender = DictCacheUtil.getDictTitle("system_user.gender", gender);
    }

    /**
     * 是否开启tab @@yes_no
     */
    @ExcelProperty(value = "是否开启tab @@yes_no")
    private String isTab;

    public void setIsTab(String isTab) {
        this.isTab = DictCacheUtil.getDictTitle("yes_no", isTab);
    }

    /**
     * 主题
     */
    @ExcelProperty(value = "主题")
    private String theme;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatar;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String description;


}