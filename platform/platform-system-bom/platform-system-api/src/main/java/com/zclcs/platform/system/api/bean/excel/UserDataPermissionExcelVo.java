package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户数据权限关联 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:55:16.457
 */
@Data
public class UserDataPermissionExcelVo {

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;


}