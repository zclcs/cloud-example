package com.zclcs.test.test.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 工程信息 Ao
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
public class ChildProjectAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 工程id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long childProjectId;

    /**
     * 工程名称
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String childProjectName;

    /**
     * 项目id
     * 默认值：0
     */
    @NotNull(message = "{required}")
    private Long projectId;

    /**
     * 建设地址
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String location;

    /**
     * 工程建设规模
     * 默认值：
     */
    @Size(max = 500, message = "{noMoreThan}")
    private String childProjectSize;

    /**
     * 合同价格（单位：分）
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
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
    @Size(max = 50, message = "{noMoreThan}")
    private String constructionPermit;

    /**
     * 施工许可证发证机关
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
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
    @Size(max = 10, message = "{noMoreThan}")
    private String permitStatus;

    /**
     * 施工许可证扫描件
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String permitAttachment;

    /**
     * 施工许可证备注
     * 默认值：
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String permitRemark;

    /**
     * 工程状态 @@child_project_status
     * 默认值：
     */
    @DictValid(value = "child_project_status", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String childProjectStatus;

    /**
     * 工程类型 @@child_project_type
     * 默认值：
     */
    @DictValid(value = "child_project_type", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String childProjectType;

    /**
     * 结构类型 @@structure_type
     * 默认值：
     */
    @DictValid(value = "structure_type", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String structureType;

    /**
     * 地基类型 @@foundation_type
     * 默认值：
     */
    @DictValid(value = "foundation_type", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String foundationType;

    /**
     * 基础类型 @@basic_type
     * 默认值：
     */
    @DictValid(value = "basic_type", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String baseType;

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
    @Size(max = 20, message = "{noMoreThan}")
    private String prjLength;

    /**
     * 跨度
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String prjSpan;

    /**
     * 结构地上层数
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String overGroundFloor;

    /**
     * 结构底下层数
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String underGroundFloor;

    /**
     * 设计使用年限
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String usefulLife;

    /**
     * 抗震设防烈度
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String seismicPrecaution;

    /**
     * 面积
     * 默认值：
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String prjArea;


}