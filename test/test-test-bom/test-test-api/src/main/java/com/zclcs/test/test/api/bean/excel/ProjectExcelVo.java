package com.zclcs.test.test.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.annotation.DictExcelValid;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectAreaHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectCityHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectDictHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectProvinceHandler;
import com.zclcs.common.export.excel.starter.annotation.ExcelSelect;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 项目信息 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-08 16:48:48.873
 */
@Data
public class ProjectExcelVo {

    /**
     * 项目id
     */
    @NotNull(message = "{required}")
    @ExcelProperty(value = "项目id")
    private Long projectId;

    /**
     * 项目编号
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "项目编号")
    private String projectCode;

    /**
     * 项目名称
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @ExcelProperty(value = "项目名称")
    private String projectName;

    /**
     * 项目简介
     */
    @Size(max = 1000, message = "{noMoreThan}")
    @ExcelProperty(value = "项目简介")
    private String description;

    /**
     * 项目分类@@project_category
     */
    @DictExcelValid(value = "project_category")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "project_category")
    @NotBlank(message = "{required}")
    @ExcelProperty(value = "项目分类")
    private String category;
    
    public void setCategory(String category) {
        this.category = DictCacheUtil.getDictTitle("project_category", category);
    }

    /**
     * 建设用地规划许可证编号
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "建设用地规划许可证编号")
    private String buildPlanNum;

    /**
     * 建设工程规划许可证编号
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "建设工程规划许可证编号")
    private String childProjectPlanNum;

    /**
     * 省
     */
    @ExcelSelect(handler = DynamicSelectProvinceHandler.class, parameter = "area_code")
    @ExcelProperty("省")
    private String province;

    /**
     * 市
     */
    @ExcelSelect(parentColumn = "省", handler = DynamicSelectCityHandler.class, parameter = "area_code")
    @ExcelProperty("市")
    private String city;
    
    /**
     * 区/县
     */
    @ExcelSelect(parentColumn = "市", handler = DynamicSelectAreaHandler.class, parameter = "area_code")
    @ExcelProperty(value = "区/县")
    private String areaCode;

    public void setAreaCode(String areaCode) {
        DictItemCacheVo area = DictCacheUtil.getDict("area_code", areaCode);
        if (area != null) {
            this.areaCode = area.getTitle();
            DictItemCacheVo city = DictCacheUtil.getDict("area_code", area.getParentValue());
            if (city != null) {
                this.city = city.getTitle();
                DictItemCacheVo province = DictCacheUtil.getDict("area_code", city.getParentValue());
                if (province != null) {
                    this.province = province.getTitle();
                }
            }
        }
    }

    /**
     * 是否市直管@@yes_no
     */
    @DictExcelValid(value = "yes_no")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "yes_no")
    @ExcelProperty(value = "是否市直管")
    private String isLeadByCity;
    
    public void setIsLeadByCity(String isLeadByCity) {
        this.isLeadByCity = DictCacheUtil.getDictTitle("yes_no", isLeadByCity);
    }

    /**
     * 总投资（单位：分）
     */
    @Size(max = 20, message = "{noMoreThan}")
    @ExcelProperty(value = "总投资（单位：分）")
    private String invest;

    /**
     * 总面积（单位：平方分米）
     */
    @Size(max = 20, message = "{noMoreThan}")
    @ExcelProperty(value = "总面积（单位：平方分米）")
    private String buildingArea;

    /**
     * 总长度（单位：厘米）
     */
    @Size(max = 20, message = "{noMoreThan}")
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
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "联系人姓名")
    private String linkMan;

    /**
     * 联系人电话
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "联系人电话")
    private String linkPhone;

    /**
     * 项目状态@@project_status
     */
    @DictExcelValid(value = "project_status")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "project_status")
    @NotBlank(message = "{required}")
    @ExcelProperty(value = "项目状态")
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
    @Size(max = 200, message = "{noMoreThan}")
    @ExcelProperty(value = "项目地址")
    private String address;

    /**
     * 立项文号
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "立项文号")
    private String approvalNum;

    /**
     * 立项级别@@approval_level
     */
    @DictExcelValid(value = "approval_level")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "approval_level")
    @ExcelProperty(value = "立项级别")
    private String approvalLevelNum;
    
    public void setApprovalLevelNum(String approvalLevelNum) {
        this.approvalLevelNum = DictCacheUtil.getDictTitle("approval_level", approvalLevelNum);
    }

    /**
     * 建设规模@@project_size
     */
    @DictExcelValid(value = "project_size")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "project_size")
    @ExcelProperty(value = "建设规模")
    private String projectSize;
    
    public void setProjectSize(String projectSize) {
        this.projectSize = DictCacheUtil.getDictTitle("project_size", projectSize);
    }

    /**
     * 建设性质@@property_num
     */
    @DictExcelValid(value = "property_num")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "property_num")
    @ExcelProperty(value = "建设性质")
    private String propertyNum;
    
    public void setPropertyNum(String propertyNum) {
        this.propertyNum = DictCacheUtil.getDictTitle("property_num", propertyNum);
    }

    /**
     * 工程用途@@function_num
     */
    @DictExcelValid(value = "function_num")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "function_num")
    @ExcelProperty(value = "工程用途")
    private String functionNum;
    
    public void setFunctionNum(String functionNum) {
        this.functionNum = DictCacheUtil.getDictTitle("function_num", functionNum);
    }

    /**
     * 职能单位@@functional_unit
     */
    @DictExcelValid(value = "functional_unit")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "functional_unit")
    @ExcelProperty(value = "职能单位")
    private String functionalUnit;
    
    public void setFunctionalUnit(String functionalUnit) {
        this.functionalUnit = DictCacheUtil.getDictTitle("functional_unit", functionalUnit);
    }

    /**
     * 是否重点项目@@yes_no
     */
    @DictExcelValid(value = "yes_no")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "yes_no")
    @ExcelProperty(value = "是否重点项目")
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