package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户 Vo
 *
 * @author zclcs
 * @since 2023-01-10 10:39:34.182
 */
@Data
public class UserExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    @ExcelProperty(value = "状态")
    private String status;

    /**
     * 性别 @@system_user.gender
     */
    @ExcelProperty(value = "性别")
    private String gender;


}