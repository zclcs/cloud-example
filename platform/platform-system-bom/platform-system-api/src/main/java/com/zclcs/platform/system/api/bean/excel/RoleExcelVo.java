package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 角色 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:33.519
 */
@Data
public class RoleExcelVo {

    /**
     * 角色id
     */
    @ExcelProperty(value = "角色id")
    private Long roleId;

    /**
     * 角色编码（唯一值）
     */
    @ExcelProperty(value = "角色编码（唯一值）")
    private String roleCode;

    /**
     * 角色名称
     */
    @ExcelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @ExcelProperty(value = "角色描述")
    private String remark;


}