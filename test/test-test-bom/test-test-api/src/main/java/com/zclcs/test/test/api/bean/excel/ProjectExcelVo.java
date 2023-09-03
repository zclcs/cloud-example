package com.zclcs.test.test.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 项目信息 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-02 17:12:14.267
 */
@Data
public class ProjectExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @ExcelProperty(value = "项目id")
    private Long projectId;

    /**
     * 项目编号
     */
    @ExcelProperty(value = "项目编号")
    private String projectCode;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String projectName;

    /**
     * 项目简介
     */
    @ExcelProperty(value = "项目简介")
    private String description;

    /**
     * 项目分类 @@project_category
     */
    @ExcelProperty(value = "项目分类 @@project_category")
    private String category;

    public void setCategory(String category) {
        this.category = DictCacheUtil.getDictTitle("project_category", category);
    }

    /**
     * 建设用地规划许可证编号
     */
    @ExcelProperty(value = "建设用地规划许可证编号")
    private String buildPlanNum;

    /**
     * 建设工程规划许可证编号
     */
    @ExcelProperty(value = "建设工程规划许可证编号")
    private String childProjectPlanNum;

    /**
     * 项目所在地 array @@area_code
     */
    @ExcelProperty(value = "项目所在地 array @@area_code")
    private String areaCode;
    
    public void setAreaCode(String areaCode) {
        this.areaCode = DictCacheUtil.getDictTitleArray("area_code", areaCode);
    }

    /**
     * 是否市直管 @@yes_no
     */
    @ExcelProperty(value = "是否市直管 @@yes_no")
    private String isLeadByCity;

    public void setIsLeadByCity(String isLeadByCity) {
        this.isLeadByCity = DictCacheUtil.getDictTitle("yes_no", isLeadByCity);
    }

    /**
     * 总投资（单位：分）
     */
    @ExcelProperty(value = "总投资（单位：分）")
    private String invest;

    /**
     * 总面积（单位：平方分米）
     */
    @ExcelProperty(value = "总面积（单位：平方分米）")
    private String buildingArea;

    /**
     * 总长度（单位：厘米）
     */
    @ExcelProperty(value = "总长度（单位：厘米）")
    private String buildingLength;

    /**
     * 实际开工日期
     */
    @ExcelProperty(value = "实际开工日期")
    private LocalDate startDate;

    /**
     * 实际竣工日期
     */
    @ExcelProperty(value = "实际竣工日期")
    private LocalDate completeDate;

    /**
     * 计划开工日期
     */
    @ExcelProperty(value = "计划开工日期")
    private LocalDate plannedStartDate;

    /**
     * 计划竣工日期
     */
    @ExcelProperty(value = "计划竣工日期")
    private LocalDate plannedCompleteDate;

    /**
     * 联系人姓名
     */
    @ExcelProperty(value = "联系人姓名")
    private String linkMan;

    /**
     * 联系人电话
     */
    @ExcelProperty(value = "联系人电话")
    private String linkPhone;

    /**
     * 项目状态 @@project_status
     */
    @ExcelProperty(value = "项目状态 @@project_status")
    private String projectStatus;

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = DictCacheUtil.getDictTitle("project_status", projectStatus);
    }

    /**
     * 经度
     */
    @ExcelProperty(value = "经度")
    private BigDecimal lng;

    /**
     * 纬度
     */
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    /**
     * 项目地址
     */
    @ExcelProperty(value = "项目地址")
    private String address;

    /**
     * 立项文号
     */
    @ExcelProperty(value = "立项文号")
    private String approvalNum;

    /**
     * 立项级别 @@approval_level
     */
    @ExcelProperty(value = "立项级别 @@approval_level")
    private String approvalLevelNum;

    public void setApprovalLevelNum(String approvalLevelNum) {
        this.approvalLevelNum = DictCacheUtil.getDictTitle("approval_level", approvalLevelNum);
    }

    /**
     * 建设规模 @@project_size
     */
    @ExcelProperty(value = "建设规模 @@project_size")
    private String projectSize;

    public void setProjectSize(String projectSize) {
        this.projectSize = DictCacheUtil.getDictTitle("project_size", projectSize);
    }

    /**
     * 建设性质 @@property_num
     */
    @ExcelProperty(value = "建设性质 @@property_num")
    private String propertyNum;

    public void setPropertyNum(String propertyNum) {
        this.propertyNum = DictCacheUtil.getDictTitle("property_num", propertyNum);
    }

    /**
     * 工程用途 @@function_num
     */
    @ExcelProperty(value = "工程用途 @@function_num")
    private String functionNum;

    public void setFunctionNum(String functionNum) {
        this.functionNum = DictCacheUtil.getDictTitle("function_num", functionNum);
    }

    /**
     * 职能单位 @@functional_unit
     */
    @ExcelProperty(value = "职能单位 @@functional_unit")
    private String functionalUnit;

    public void setFunctionalUnit(String functionalUnit) {
        this.functionalUnit = DictCacheUtil.getDictTitle("functional_unit", functionalUnit);
    }

    /**
     * 是否重点项目 @@yes_no
     */
    @ExcelProperty(value = "是否重点项目 @@yes_no")
    private String majorProject;

    public void setMajorProject(String majorProject) {
        this.majorProject = DictCacheUtil.getDictTitle("yes_no", majorProject);
    }

    /**
     * 最后一次考勤时间
     */
    @ExcelProperty(value = "最后一次考勤时间")
    private LocalDateTime lastAttendTime;


}