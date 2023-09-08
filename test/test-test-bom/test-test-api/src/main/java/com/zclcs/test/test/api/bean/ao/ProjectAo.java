package com.zclcs.test.test.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 项目信息 Ao
 *
 * @author zclcs
 * @since 2023-09-08 16:48:48.873
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long projectId;

    /**
     * 项目编号
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String projectCode;

    /**
     * 项目名称
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String projectName;

    /**
     * 项目简介
     * 默认值：
     */
    @Size(max = 1000, message = "{noMoreThan}")
    private String description;

    /**
     * 项目分类@@project_category
     * 默认值：
     */
    @DictValid(value = "project_category", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String category;

    /**
     * 建设用地规划许可证编号
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String buildPlanNum;

    /**
     * 建设工程规划许可证编号
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String childProjectPlanNum;

    /**
     * 项目所在地@@area_code@@tree
     * 默认值：
     */
    @DictValid(value = "area_code", message = "{dict}")
    @Size(max = 150, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String areaCode;

    /**
     * 是否市直管@@yes_no
     * 默认值：
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    private String isLeadByCity;

    /**
     * 总投资（单位：分）
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String invest;

    /**
     * 总面积（单位：平方分米）
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String buildingArea;

    /**
     * 总长度（单位：厘米）
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
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
    @Size(max = 50, message = "{noMoreThan}")
    private String linkMan;

    /**
     * 联系人电话
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String linkPhone;

    /**
     * 项目状态@@project_status
     * 默认值：
     */
    @DictValid(value = "project_status", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String projectStatus;

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
    @Size(max = 200, message = "{noMoreThan}")
    private String address;

    /**
     * 立项文号
     * 默认值：
     */
    @Size(max = 50, message = "{noMoreThan}")
    private String approvalNum;

    /**
     * 立项级别@@approval_level
     * 默认值：
     */
    @DictValid(value = "approval_level", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    private String approvalLevelNum;

    /**
     * 建设规模@@project_size
     * 默认值：
     */
    @DictValid(value = "project_size", message = "{dict}")
    @Size(max = 2, message = "{noMoreThan}")
    private String projectSize;

    /**
     * 建设性质@@property_num
     * 默认值：
     */
    @DictValid(value = "property_num", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    private String propertyNum;

    /**
     * 工程用途@@function_num
     * 默认值：
     */
    @DictValid(value = "function_num", message = "{dict}")
    @Size(max = 3, message = "{noMoreThan}")
    private String functionNum;

    /**
     * 职能单位@@functional_unit
     * 默认值：
     */
    @DictValid(value = "functional_unit", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    private String functionalUnit;

    /**
     * 是否重点项目@@yes_no
     * 默认值：
     */
    @DictValid(value = "yes_no", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    private String majorProject;

    /**
     * 最后一次考勤时间
     * 默认值：
     */
    private LocalDateTime lastAttendTime;


}