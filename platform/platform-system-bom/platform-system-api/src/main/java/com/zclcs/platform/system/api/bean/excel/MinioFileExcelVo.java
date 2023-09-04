package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * minio文件 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:54:40.426
 */
@Data
public class MinioFileExcelVo {

    /**
     * 文件id
     */
    @ExcelProperty(value = "文件id")
    private String id;

    /**
     * 桶id
     */
    @ExcelProperty(value = "桶id")
    private Long bucketId;

    /**
     * 文件名称
     */
    @ExcelProperty(value = "文件名称")
    private String fileName;

    /**
     * 原文件名称
     */
    @ExcelProperty(value = "原文件名称")
    private String originalFileName;

    /**
     * 文件路径
     */
    @ExcelProperty(value = "文件路径")
    private String filePath;

    /**
     * 内容类型
     */
    @ExcelProperty(value = "内容类型")
    private String contentType;


}