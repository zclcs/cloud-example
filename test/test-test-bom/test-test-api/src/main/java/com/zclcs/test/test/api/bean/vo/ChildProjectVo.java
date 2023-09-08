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

/**
 * 工程信息 Vo
 *
 * @author zclcs
 * @since 2023-09-08 16:48:53.770
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChildProjectVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 工程id
     * 默认值：
     */
    private Long childProjectId;

    /**
     * 工程名称
     * 默认值：
     */
    private String childProjectName;

    /**
     * 项目id
     * 默认值：0
     */
    private Long projectId;

    /**
     * 建设地址
     * 默认值：
     */
    private String location;

    /**
     * 工程建设规模
     * 默认值：
     */
    private String childProjectSize;

    /**
     * 合同价格（单位：分）
     * 默认值：
     */
    private String price;

    /**
     * 合同工期起始时间
     * 默认值：
     */
    private LocalDate contractStartDate;

    /**
     * 合同工期终止时间
     * 默认值：
     */
    private LocalDate contractEndDate;

    /**
     * 施工许可证号
     * 默认值：
     */
    private String constructionPermit;

    /**
     * 施工许可证发证机关
     * 默认值：
     */
    private String permitGrantOrg;

    /**
     * 施工许可证发证日期
     * 默认值：
     */
    private LocalDate permitGrantDate;

    /**
     * 施工许可证状态
     * 默认值：
     */
    private String permitStatus;

    /**
     * 施工许可证扫描件
     * 默认值：
     */
    private String permitAttachment;

    /**
     * 施工许可证备注
     * 默认值：
     */
    private String permitRemark;

    /**
     * 工程状态 @@child_project_status
     * 默认值：
     */
    private String childProjectStatus;

    /**
     * 工程状态 @@child_project_status
     */
    private String childProjectStatusText;

    public String getChildProjectStatusText() {
        return DictCacheUtil.getDictTitle("child_project_status", this.childProjectStatus);
    }

    /**
     * 工程类型 @@child_project_type
     * 默认值：
     */
    private String childProjectType;

    /**
     * 工程类型 @@child_project_type
     */
    private String childProjectTypeText;

    public String getChildProjectTypeText() {
        return DictCacheUtil.getDictTitle("child_project_type", this.childProjectType);
    }

    /**
     * 结构类型 @@structure_type
     * 默认值：
     */
    private String structureType;

    /**
     * 结构类型 @@structure_type
     */
    private String structureTypeText;

    public String getStructureTypeText() {
        return DictCacheUtil.getDictTitle("structure_type", this.structureType);
    }

    /**
     * 地基类型 @@foundation_type
     * 默认值：
     */
    private String foundationType;

    /**
     * 地基类型 @@foundation_type
     */
    private String foundationTypeText;

    public String getFoundationTypeText() {
        return DictCacheUtil.getDictTitle("foundation_type", this.foundationType);
    }

    /**
     * 基础类型 @@basic_type
     * 默认值：
     */
    private String baseType;

    /**
     * 基础类型 @@basic_type
     */
    private String baseTypeText;

    public String getBaseTypeText() {
        return DictCacheUtil.getDictTitle("basic_type", this.baseType);
    }

    /**
     * 计划开工日期
     * 默认值：
     */
    private LocalDate prjStartDate;

    /**
     * 计划竣工日期
     * 默认值：
     */
    private LocalDate prjCompleteDate;

    /**
     * 长度
     * 默认值：
     */
    private String prjLength;

    /**
     * 跨度
     * 默认值：
     */
    private String prjSpan;

    /**
     * 结构地上层数
     * 默认值：
     */
    private String overGroundFloor;

    /**
     * 结构底下层数
     * 默认值：
     */
    private String underGroundFloor;

    /**
     * 设计使用年限
     * 默认值：
     */
    private String usefulLife;

    /**
     * 抗震设防烈度
     * 默认值：
     */
    private String seismicPrecaution;

    /**
     * 面积
     * 默认值：
     */
    private String prjArea;


}