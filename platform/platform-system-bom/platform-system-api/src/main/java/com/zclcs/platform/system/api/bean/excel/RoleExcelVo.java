package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:33.519
 */
@Data
public class RoleExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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