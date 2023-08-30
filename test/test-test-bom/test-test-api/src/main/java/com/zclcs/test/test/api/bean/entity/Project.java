package com.zclcs.test.test.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 项目信息 Entity
 *
 * @author zclcs
 * @since 2023-08-16 14:53:10.430
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("test_project")
public class Project extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @Id(value = "project_id", keyType = KeyType.Auto)
    private Long projectId;

    /**
     * 项目编号
     */
    @Column("project_code")
    private String projectCode;

    /**
     * 项目名称
     */
    @Column("project_name")
    private String projectName;

    /**
     * 项目简介
     */
    @Column("description")
    private String description;

    /**
     * 项目分类 @@project_category
     */
    @Column("category")
    private String category;

    /**
     * 建设用地规划许可证编号
     */
    @Column("build_plan_num")
    private String buildPlanNum;

    /**
     * 建设工程规划许可证编号
     */
    @Column("child_project_plan_num")
    private String childProjectPlanNum;

    /**
     * 项目所在地 array @@area_code
     */
    @Column("area_code")
    private String areaCode;

    /**
     * 是否市直管 @@yes_no
     */
    @Column("is_lead_by_city")
    private String isLeadByCity;

    /**
     * 总投资（单位：分）
     */
    @Column("invest")
    private String invest;

    /**
     * 总面积（单位：平方分米）
     */
    @Column("building_area")
    private String buildingArea;

    /**
     * 总长度（单位：厘米）
     */
    @Column("building_length")
    private String buildingLength;

    /**
     * 实际开工日期
     */
    @Column("start_date")
    private LocalDate startDate;

    /**
     * 实际竣工日期
     */
    @Column("complete_date")
    private LocalDate completeDate;

    /**
     * 计划开工日期
     */
    @Column("planned_start_date")
    private LocalDate plannedStartDate;

    /**
     * 计划竣工日期
     */
    @Column("planned_complete_date")
    private LocalDate plannedCompleteDate;

    /**
     * 联系人姓名
     */
    @Column("link_man")
    private String linkMan;

    /**
     * 联系人电话
     */
    @Column("link_phone")
    private String linkPhone;

    /**
     * 项目状态 @@project_status
     */
    @Column("project_status")
    private String projectStatus;

    /**
     * 经度
     */
    @Column("lng")
    private BigDecimal lng;

    /**
     * 纬度
     */
    @Column("lat")
    private BigDecimal lat;

    /**
     * 项目地址
     */
    @Column("address")
    private String address;

    /**
     * 立项文号
     */
    @Column("approval_num")
    private String approvalNum;

    /**
     * 立项级别 @@approval_level
     */
    @Column("approval_level_num")
    private String approvalLevelNum;

    /**
     * 建设规模 @@project_size
     */
    @Column("project_size")
    private String projectSize;

    /**
     * 建设性质 @@property_num
     */
    @Column("property_num")
    private String propertyNum;

    /**
     * 工程用途 @@function_num
     */
    @Column("function_num")
    private String functionNum;

    /**
     * 职能单位 @@functional_unit
     */
    @Column("functional_unit")
    private String functionalUnit;

    /**
     * 是否重点项目 @@yes_no
     */
    @Column("major_project")
    private String majorProject;

    /**
     * 最后一次考勤时间
     */
    @Column("last_attend_time")
    private LocalDateTime lastAttendTime;


}