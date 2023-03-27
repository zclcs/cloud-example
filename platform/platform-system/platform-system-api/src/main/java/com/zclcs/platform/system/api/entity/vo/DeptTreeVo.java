package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.common.core.base.Tree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "DeptTreeVo对象", description = "部门树")
public class DeptTreeVo extends Tree<DeptVo> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "是否顶级部门")
    private Boolean harPar;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "排序")
    private Double orderNum;

    @Schema(description = "创建时间")
    private LocalDateTime createAt;

}
