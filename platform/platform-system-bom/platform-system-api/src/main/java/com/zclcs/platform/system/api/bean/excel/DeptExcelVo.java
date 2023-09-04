package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 部门 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:38.826
 */
@Data
public class DeptExcelVo {

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 部门编码
     */
    @ExcelProperty(value = "部门编码")
    private String deptCode;

    /**
     * 上级部门编码
     */
    @ExcelProperty(value = "上级部门编码")
    private String parentCode;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Double orderNum;


}