package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户角色关联 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:58.035
 */
@Data
public class UserRoleExcelVo {

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 角色id
     */
    @ExcelProperty(value = "角色id")
    private Long roleId;


}