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
import java.time.LocalDate;

/**
 * 工程信息 Entity
 *
 * @author zclcs
 * @since 2023-08-16 14:53:25.234
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("test_child_project")
public class ChildProject extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 工程id
     */
    @Id(value = "child_project_id", keyType = KeyType.Auto)
    private Long childProjectId;

    /**
     * 工程名称
     */
    @Column("child_project_name")
    private String childProjectName;

    /**
     * 项目id
     */
    @Column("project_id")
    private Long projectId;

    /**
     * 建设地址
     */
    @Column("location")
    private String location;

    /**
     * 工程建设规模
     */
    @Column("child_project_size")
    private String childProjectSize;

    /**
     * 合同价格（单位：分）
     */
    @Column("price")
    private String price;

    /**
     * 合同工期起始时间
     */
    @Column("contract_start_date")
    private LocalDate contractStartDate;

    /**
     * 合同工期终止时间
     */
    @Column("contract_end_date")
    private LocalDate contractEndDate;

    /**
     * 施工许可证号
     */
    @Column("construction_permit")
    private String constructionPermit;

    /**
     * 施工许可证发证机关
     */
    @Column("permit_grant_org")
    private String permitGrantOrg;

    /**
     * 施工许可证发证日期
     */
    @Column("permit_grant_date")
    private LocalDate permitGrantDate;

    /**
     * 施工许可证状态
     */
    @Column("permit_status")
    private String permitStatus;

    /**
     * 施工许可证扫描件
     */
    @Column("permit_attachment")
    private String permitAttachment;

    /**
     * 施工许可证备注
     */
    @Column("permit_remark")
    private String permitRemark;

    /**
     * 工程状态 @@child_project_status
     */
    @Column("child_project_status")
    private String childProjectStatus;

    /**
     * 工程类型 @@child_project_type
     */
    @Column("child_project_type")
    private String childProjectType;

    /**
     * 结构类型 @@structure_type
     */
    @Column("structure_type")
    private String structureType;

    /**
     * 地基类型 @@foundation_type
     */
    @Column("foundation_type")
    private String foundationType;

    /**
     * 基础类型 @@basic_type
     */
    @Column("base_type")
    private String baseType;

    /**
     * 计划开工日期
     */
    @Column("prj_start_date")
    private LocalDate prjStartDate;

    /**
     * 计划竣工日期
     */
    @Column("prj_complete_date")
    private LocalDate prjCompleteDate;

    /**
     * 长度
     */
    @Column("prj_length")
    private String prjLength;

    /**
     * 跨度
     */
    @Column("prj_span")
    private String prjSpan;

    /**
     * 结构地上层数
     */
    @Column("over_ground_floor")
    private String overGroundFloor;

    /**
     * 结构底下层数
     */
    @Column("under_ground_floor")
    private String underGroundFloor;

    /**
     * 设计使用年限
     */
    @Column("useful_life")
    private String usefulLife;

    /**
     * 抗震设防烈度
     */
    @Column("seismic_precaution")
    private String seismicPrecaution;

    /**
     * 面积
     */
    @Column("prj_area")
    private String prjArea;


}