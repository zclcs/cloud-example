package com.zclcs.test.test.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 工程信息 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-04 20:04:57.706
 */
@Data
public class ChildProjectExcelVo {

    /**
     * 工程id
     */
    @ExcelProperty(value = "工程id")
    private Long childProjectId;

    /**
     * 工程名称
     */
    @ExcelProperty(value = "工程名称")
    private String childProjectName;

    /**
     * 项目id
     */
    @ExcelProperty(value = "项目id")
    private Long projectId;

    /**
     * 建设地址
     */
    @ExcelProperty(value = "建设地址")
    private String location;

    /**
     * 工程建设规模
     */
    @ExcelProperty(value = "工程建设规模")
    private String childProjectSize;

    /**
     * 合同价格（单位：分）
     */
    @ExcelProperty(value = "合同价格（单位：分）")
    private String price;

    /**
     * 合同工期起始时间
     */
    @ExcelProperty(value = "合同工期起始时间")
    private LocalDate contractStartDate;

    /**
     * 合同工期终止时间
     */
    @ExcelProperty(value = "合同工期终止时间")
    private LocalDate contractEndDate;

    /**
     * 施工许可证号
     */
    @ExcelProperty(value = "施工许可证号")
    private String constructionPermit;

    /**
     * 施工许可证发证机关
     */
    @ExcelProperty(value = "施工许可证发证机关")
    private String permitGrantOrg;

    /**
     * 施工许可证发证日期
     */
    @ExcelProperty(value = "施工许可证发证日期")
    private LocalDate permitGrantDate;

    /**
     * 施工许可证状态
     */
    @ExcelProperty(value = "施工许可证状态")
    private String permitStatus;

    /**
     * 施工许可证扫描件
     */
    @ExcelProperty(value = "施工许可证扫描件")
    private String permitAttachment;

    /**
     * 施工许可证备注
     */
    @ExcelProperty(value = "施工许可证备注")
    private String permitRemark;

    /**
     * 工程状态 @@child_project_status
     */
    @ExcelProperty(value = "工程状态 @@child_project_status")
    private String childProjectStatus;

    public void setChildProjectStatus(String childProjectStatus) {
        this.childProjectStatus = DictCacheUtil.getDictTitle("child_project_status", childProjectStatus);
    }

    /**
     * 工程类型 @@child_project_type
     */
    @ExcelProperty(value = "工程类型 @@child_project_type")
    private String childProjectType;

    public void setChildProjectType(String childProjectType) {
        this.childProjectType = DictCacheUtil.getDictTitle("child_project_type", childProjectType);
    }

    /**
     * 结构类型 @@structure_type
     */
    @ExcelProperty(value = "结构类型 @@structure_type")
    private String structureType;

    public void setStructureType(String structureType) {
        this.structureType = DictCacheUtil.getDictTitle("structure_type", structureType);
    }

    /**
     * 地基类型 @@foundation_type
     */
    @ExcelProperty(value = "地基类型 @@foundation_type")
    private String foundationType;

    public void setFoundationType(String foundationType) {
        this.foundationType = DictCacheUtil.getDictTitle("foundation_type", foundationType);
    }

    /**
     * 基础类型 @@basic_type
     */
    @ExcelProperty(value = "基础类型 @@basic_type")
    private String baseType;

    public void setBaseType(String baseType) {
        this.baseType = DictCacheUtil.getDictTitle("basic_type", baseType);
    }

    /**
     * 计划开工日期
     */
    @ExcelProperty(value = "计划开工日期")
    private LocalDate prjStartDate;

    /**
     * 计划竣工日期
     */
    @ExcelProperty(value = "计划竣工日期")
    private LocalDate prjCompleteDate;

    /**
     * 长度
     */
    @ExcelProperty(value = "长度")
    private String prjLength;

    /**
     * 跨度
     */
    @ExcelProperty(value = "跨度")
    private String prjSpan;

    /**
     * 结构地上层数
     */
    @ExcelProperty(value = "结构地上层数")
    private String overGroundFloor;

    /**
     * 结构底下层数
     */
    @ExcelProperty(value = "结构底下层数")
    private String underGroundFloor;

    /**
     * 设计使用年限
     */
    @ExcelProperty(value = "设计使用年限")
    private String usefulLife;

    /**
     * 抗震设防烈度
     */
    @ExcelProperty(value = "抗震设防烈度")
    private String seismicPrecaution;

    /**
     * 面积
     */
    @ExcelProperty(value = "面积")
    private String prjArea;


}