package com.zclcs.test.test.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.Array;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 项目信息 Vo
 *
 * @author zclcs
 * @date 2023-08-16 14:53:10.430
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目简介
     */
    private String description;

    /**
     * 项目分类 @@project_category
     */
    @DictText(value = "project_category")
    private String category;

    /**
     * 建设用地规划许可证编号
     */
    private String buildPlanNum;

    /**
     * 建设工程规划许可证编号
     */
    private String childProjectPlanNum;

    /**
     * 项目所在地 array @@area_code
     */
    @DictText(value = "area_code", array = @Array)
    private String areaCode;

    /**
     * 是否市直管 @@yes_no
     */
    @DictText(value = "yes_no")
    private String isLeadByCity;

    /**
     * 总投资（单位：分）
     */
    private String invest;

    /**
     * 总面积（单位：平方分米）
     */
    private String buildingArea;

    /**
     * 总长度（单位：厘米）
     */
    private String buildingLength;

    /**
     * 实际开工日期
     */
    private LocalDate startDate;

    /**
     * 实际竣工日期
     */
    private LocalDate completeDate;

    /**
     * 计划开工日期
     */
    private LocalDate plannedStartDate;

    /**
     * 计划竣工日期
     */
    private LocalDate plannedCompleteDate;

    /**
     * 联系人姓名
     */
    private String linkMan;

    /**
     * 联系人电话
     */
    private String linkPhone;

    /**
     * 项目状态 @@project_status
     */
    @DictText(value = "project_status")
    private String projectStatus;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 项目地址
     */
    private String address;

    /**
     * 立项文号
     */
    private String approvalNum;

    /**
     * 立项级别 @@approval_level
     */
    @DictText(value = "approval_level")
    private String approvalLevelNum;

    /**
     * 建设规模 @@project_size
     */
    @DictText(value = "project_size")
    private String projectSize;

    /**
     * 建设性质 @@property_num
     */
    @DictText(value = "property_num")
    private String propertyNum;

    /**
     * 工程用途 @@function_num
     */
    @DictText(value = "function_num")
    private String functionNum;

    /**
     * 职能单位 @@functional_unit
     */
    @DictText(value = "functional_unit")
    private String functionalUnit;

    /**
     * 是否重点项目 @@yes_no
     */
    @DictText(value = "yes_no")
    private String majorProject;

    /**
     * 最后一次考勤时间
     */
    private LocalDateTime lastAttendTime;


}