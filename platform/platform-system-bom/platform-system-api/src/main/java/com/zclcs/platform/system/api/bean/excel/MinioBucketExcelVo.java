package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * minio桶 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:44.135
 */
@Data
public class MinioBucketExcelVo {

    /**
     * 桶id
     */
    @ExcelProperty(value = "桶id")
    private Long id;

    /**
     * 桶名称
     */
    @ExcelProperty(value = "桶名称")
    private String bucketName;

    /**
     * 桶权限
     */
    @ExcelProperty(value = "桶权限")
    private String bucketPolicy;


}