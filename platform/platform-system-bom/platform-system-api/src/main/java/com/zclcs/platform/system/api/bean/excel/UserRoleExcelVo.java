package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户角色关联 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:58.035
 */
@Data
public class UserRoleExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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