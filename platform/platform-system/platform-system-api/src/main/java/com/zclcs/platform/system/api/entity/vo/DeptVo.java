package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 部门 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DeptVo对象", description = "部门")
public class DeptVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "上级部门id")
    private Long parentId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "排序")
    private Double orderNum;

    @Schema(description = "创建时间-开始")
    private LocalDate createTimeFrom;

    @Schema(description = "创建时间-结束")
    private LocalDate createTimeTo;


}