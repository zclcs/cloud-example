package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:38.826
 */
@Data
public class DeptExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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