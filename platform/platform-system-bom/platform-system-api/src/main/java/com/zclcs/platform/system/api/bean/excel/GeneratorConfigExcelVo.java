package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

/**
 * 代码生成配置 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 20:04:43.904
 */
@Data
public class GeneratorConfigExcelVo {

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 服务名
     */
    @ExcelProperty(value = "服务名")
    private String serverName;

    /**
     * 作者
     */
    @ExcelProperty(value = "作者")
    private String author;

    /**
     * 基础包名
     */
    @ExcelProperty(value = "基础包名")
    private String basePackage;

    /**
     * entity文件存放路径
     */
    @ExcelProperty(value = "entity文件存放路径")
    private String entityPackage;

    /**
     * 入参
     */
    @ExcelProperty(value = "入参")
    private String aoPackage;

    /**
     * 出参
     */
    @ExcelProperty(value = "出参")
    private String voPackage;

    /**
     * mapper文件存放路径
     */
    @ExcelProperty(value = "mapper文件存放路径")
    private String mapperPackage;

    /**
     * mapper xml文件存放路径
     */
    @ExcelProperty(value = "mapper xml文件存放路径")
    private String mapperXmlPackage;

    /**
     * service文件存放路径
     */
    @ExcelProperty(value = "service文件存放路径")
    private String servicePackage;

    /**
     * serviceImpl文件存放路径
     */
    @ExcelProperty(value = "serviceImpl文件存放路径")
    private String serviceImplPackage;

    /**
     * controller文件存放路径
     */
    @ExcelProperty(value = "controller文件存放路径")
    private String controllerPackage;

    /**
     * 是否去除前缀 @@yes_no
     */
    @ExcelProperty(value = "是否去除前缀 @@yes_no")
    private String isTrim;

    public void setIsTrim(String isTrim) {
        this.isTrim = DictCacheUtil.getDictTitle("yes_no", isTrim);
    }

    /**
     * 前缀内容
     */
    @ExcelProperty(value = "前缀内容")
    private String trimValue;

    /**
     * 需要排除的字段
     */
    @ExcelProperty(value = "需要排除的字段")
    private String excludeColumns;

    /**
     * 版本 @@generate.version
     */
    @ExcelProperty(value = "版本 @@generate.version")
    private String genVersion;

    public void setGenVersion(String genVersion) {
        this.genVersion = DictCacheUtil.getDictTitle("generate.version", genVersion);
    }

    /**
     * 缓存实体
     */
    @ExcelProperty(value = "缓存实体")
    private String cacheVoPackage;

    /**
     * excel实体
     */
    @ExcelProperty(value = "excel实体")
    private String excelVoPackage;


}