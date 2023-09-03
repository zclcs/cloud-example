package com.zclcs.test.test.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 项目信息 Vo
 *
 * @author zclcs
 * @since 2023-09-02 17:12:14.267
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     * 默认值：
     */
    private Long projectId;

    /**
     * 项目编号
     * 默认值：
     */
    private String projectCode;

    /**
     * 项目名称
     * 默认值：
     */
    private String projectName;

    /**
     * 项目简介
     * 默认值：
     */
    private String description;

    /**
     * 项目分类 @@project_category
     * 默认值：
     */
    private String category;

    /**
     * 项目分类 @@project_category
     */
    private String categoryText;

    public String getCategoryText() {
        return DictCacheUtil.getDictTitle("project_category", this.category);
    }

    /**
     * 建设用地规划许可证编号
     * 默认值：
     */
    private String buildPlanNum;

    /**
     * 建设工程规划许可证编号
     * 默认值：
     */
    private String childProjectPlanNum;

    /**
     * 项目所在地 array @@area_code
     * 默认值：
     */
    private String areaCode;

    /**
     * 项目所在地 array @@area_code
     */
    private String areaCodeText;

    public String getAreaCodeText() {
        return DictCacheUtil.getDictTitleArray("area_code", this.areaCode);
    }

    /**
     * 是否市直管 @@yes_no
     * 默认值：
     */
    private String isLeadByCity;

    /**
     * 是否市直管 @@yes_no
     */
    private String isLeadByCityText;

    public String getIsLeadByCityText() {
        return DictCacheUtil.getDictTitle("yes_no", this.isLeadByCity);
    }

    /**
     * 总投资（单位：分）
     * 默认值：
     */
    private String invest;

    /**
     * 总面积（单位：平方分米）
     * 默认值：
     */
    private String buildingArea;

    /**
     * 总长度（单位：厘米）
     * 默认值：
     */
    private String buildingLength;

    /**
     * 实际开工日期
     * 默认值：
     */
    private LocalDate startDate;

    /**
     * 实际竣工日期
     * 默认值：
     */
    private LocalDate completeDate;

    /**
     * 计划开工日期
     * 默认值：
     */
    private LocalDate plannedStartDate;

    /**
     * 计划竣工日期
     * 默认值：
     */
    private LocalDate plannedCompleteDate;

    /**
     * 联系人姓名
     * 默认值：
     */
    private String linkMan;

    /**
     * 联系人电话
     * 默认值：
     */
    private String linkPhone;

    /**
     * 项目状态 @@project_status
     * 默认值：
     */
    private String projectStatus;

    /**
     * 项目状态 @@project_status
     */
    private String projectStatusText;

    public String getProjectStatusText() {
        return DictCacheUtil.getDictTitle("project_status", this.projectStatus);
    }

    /**
     * 经度
     * 默认值：0.000000000000000
     */
    private BigDecimal lng;

    /**
     * 纬度
     * 默认值：0.000000000000000
     */
    private BigDecimal lat;

    /**
     * 项目地址
     * 默认值：
     */
    private String address;

    /**
     * 立项文号
     * 默认值：
     */
    private String approvalNum;

    /**
     * 立项级别 @@approval_level
     * 默认值：
     */
    private String approvalLevelNum;

    /**
     * 立项级别 @@approval_level
     */
    private String approvalLevelNumText;

    public String getApprovalLevelNumText() {
        return DictCacheUtil.getDictTitle("approval_level", this.approvalLevelNum);
    }

    /**
     * 建设规模 @@project_size
     * 默认值：
     */
    private String projectSize;

    /**
     * 建设规模 @@project_size
     */
    private String projectSizeText;

    public String getProjectSizeText() {
        return DictCacheUtil.getDictTitle("project_size", this.projectSize);
    }

    /**
     * 建设性质 @@property_num
     * 默认值：
     */
    private String propertyNum;

    /**
     * 建设性质 @@property_num
     */
    private String propertyNumText;

    public String getPropertyNumText() {
        return DictCacheUtil.getDictTitle("property_num", this.propertyNum);
    }

    /**
     * 工程用途 @@function_num
     * 默认值：
     */
    private String functionNum;

    /**
     * 工程用途 @@function_num
     */
    private String functionNumText;

    public String getFunctionNumText() {
        return DictCacheUtil.getDictTitle("function_num", this.functionNum);
    }

    /**
     * 职能单位 @@functional_unit
     * 默认值：
     */
    private String functionalUnit;

    /**
     * 职能单位 @@functional_unit
     */
    private String functionalUnitText;

    public String getFunctionalUnitText() {
        return DictCacheUtil.getDictTitle("functional_unit", this.functionalUnit);
    }

    /**
     * 是否重点项目 @@yes_no
     * 默认值：
     */
    private String majorProject;

    /**
     * 是否重点项目 @@yes_no
     */
    private String majorProjectText;

    public String getMajorProjectText() {
        return DictCacheUtil.getDictTitle("yes_no", this.majorProject);
    }

    /**
     * 最后一次考勤时间
     * 默认值：
     */
    private LocalDateTime lastAttendTime;


}