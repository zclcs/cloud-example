package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 部门 Ao
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
@Schema(name = "DeptAo对象", description = "部门")
public class DeptAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "部门id")
    private Long deptId;

    @NotNull(message = "{required}")
    @Schema(description = "上级部门id", required = true)
    private Long parentId;

    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "部门名称", required = true)
    private String deptName;

    @Schema(description = "排序")
    private Double orderNum;


}