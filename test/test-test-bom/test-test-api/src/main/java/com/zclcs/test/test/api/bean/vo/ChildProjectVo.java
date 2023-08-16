package com.zclcs.test.test.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 工程信息 Vo
 *
 * @author zclcs
 * @date 2023-08-16 14:53:25.234
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChildProjectVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 工程id
     */
    private Long childProjectId;

    /**
     * 工程名称
     */
    private String childProjectName;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 建设地址
     */
    private String location;

    /**
     * 工程建设规模
     */
    private String childProjectSize;

    /**
     * 合同价格（单位：分）
     */
    private String price;

    /**
     * 合同工期起始时间
     */
    private LocalDate contractStartDate;

    /**
     * 合同工期终止时间
     */
    private LocalDate contractEndDate;

    /**
     * 施工许可证号
     */
    private String constructionPermit;

    /**
     * 施工许可证发证机关
     */
    private String permitGrantOrg;

    /**
     * 施工许可证发证日期
     */
    private LocalDate permitGrantDate;

    /**
     * 施工许可证状态
     */
    private String permitStatus;

    /**
     * 施工许可证扫描件
     */
    private String permitAttachment;

    /**
     * 施工许可证备注
     */
    private String permitRemark;

    /**
     * 工程状态 @@child_project_status
     */
    @DictText(value = "child_project_status")
    private String childProjectStatus;

    /**
     * 工程类型 @@child_project_type
     */
    @DictText(value = "child_project_type")
    private String childProjectType;

    /**
     * 结构类型 @@structure_type
     */
    @DictText(value = "structure_type")
    private String structureType;

    /**
     * 地基类型 @@foundation_type
     */
    @DictText(value = "foundation_type")
    private String foundationType;

    /**
     * 基础类型 @@basic_type
     */
    @DictText(value = "basic_type")
    private String baseType;

    /**
     * 计划开工日期
     */
    private LocalDate prjStartDate;

    /**
     * 计划竣工日期
     */
    private LocalDate prjCompleteDate;

    /**
     * 长度
     */
    private String prjLength;

    /**
     * 跨度
     */
    private String prjSpan;

    /**
     * 结构地上层数
     */
    private String overGroundFloor;

    /**
     * 结构底下层数
     */
    private String underGroundFloor;

    /**
     * 设计使用年限
     */
    private String usefulLife;

    /**
     * 抗震设防烈度
     */
    private String seismicPrecaution;

    /**
     * 面积
     */
    private String prjArea;


}