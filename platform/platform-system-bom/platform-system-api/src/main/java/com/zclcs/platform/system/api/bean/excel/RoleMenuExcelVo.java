package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 角色菜单关联 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:21.568
 */
@Data
public class RoleMenuExcelVo {

    /**
     * 角色id
     */
    @ExcelProperty(value = "角色id")
    private Long roleId;

    /**
     * 菜单id
     */
    @ExcelProperty(value = "菜单id")
    private Long menuId;


}