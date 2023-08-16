package com.zclcs.test.test.api.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 工程信息 Entity
 *
 * @author zclcs
 * @date 2023-08-16 14:53:25.234
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("test_child_project")
public class ChildProject extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 工程id
     */
    @TableId(value = "child_project_id", type = IdType.AUTO)
    private Long childProjectId;

    /**
     * 工程名称
     */
    @TableField("child_project_name")
    private String childProjectName;

    /**
     * 项目id
     */
    @TableField("project_id")
    private Long projectId;

    /**
     * 建设地址
     */
    @TableField("location")
    private String location;

    /**
     * 工程建设规模
     */
    @TableField("child_project_size")
    private String childProjectSize;

    /**
     * 合同价格（单位：分）
     */
    @TableField("price")
    private String price;

    /**
     * 合同工期起始时间
     */
    @TableField("contract_start_date")
    private LocalDate contractStartDate;

    /**
     * 合同工期终止时间
     */
    @TableField("contract_end_date")
    private LocalDate contractEndDate;

    /**
     * 施工许可证号
     */
    @TableField("construction_permit")
    private String constructionPermit;

    /**
     * 施工许可证发证机关
     */
    @TableField("permit_grant_org")
    private String permitGrantOrg;

    /**
     * 施工许可证发证日期
     */
    @TableField("permit_grant_date")
    private LocalDate permitGrantDate;

    /**
     * 施工许可证状态
     */
    @TableField("permit_status")
    private String permitStatus;

    /**
     * 施工许可证扫描件
     */
    @TableField("permit_attachment")
    private String permitAttachment;

    /**
     * 施工许可证备注
     */
    @TableField("permit_remark")
    private String permitRemark;

    /**
     * 工程状态 @@child_project_status
     */
    @TableField("child_project_status")
    private String childProjectStatus;

    /**
     * 工程类型 @@child_project_type
     */
    @TableField("child_project_type")
    private String childProjectType;

    /**
     * 结构类型 @@structure_type
     */
    @TableField("structure_type")
    private String structureType;

    /**
     * 地基类型 @@foundation_type
     */
    @TableField("foundation_type")
    private String foundationType;

    /**
     * 基础类型 @@basic_type
     */
    @TableField("base_type")
    private String baseType;

    /**
     * 计划开工日期
     */
    @TableField("prj_start_date")
    private LocalDate prjStartDate;

    /**
     * 计划竣工日期
     */
    @TableField("prj_complete_date")
    private LocalDate prjCompleteDate;

    /**
     * 长度
     */
    @TableField("prj_length")
    private String prjLength;

    /**
     * 跨度
     */
    @TableField("prj_span")
    private String prjSpan;

    /**
     * 结构地上层数
     */
    @TableField("over_ground_floor")
    private String overGroundFloor;

    /**
     * 结构底下层数
     */
    @TableField("under_ground_floor")
    private String underGroundFloor;

    /**
     * 设计使用年限
     */
    @TableField("useful_life")
    private String usefulLife;

    /**
     * 抗震设防烈度
     */
    @TableField("seismic_precaution")
    private String seismicPrecaution;

    /**
     * 面积
     */
    @TableField("prj_area")
    private String prjArea;


}