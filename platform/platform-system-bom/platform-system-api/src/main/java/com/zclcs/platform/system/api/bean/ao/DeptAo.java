package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门 Ao
 *
 * @author zclcs
 * @since 2023-01-10 10:39:10.151
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeptAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long deptId;

    /**
     * 部门编码
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String deptCode;

    /**
     * 上级部门编码 不填默认顶级
     */
    @Size(max = 100, message = "{noMoreThan}")
    private String parentCode;

    /**
     * 部门名称
     */
    @Size(max = 100, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String deptName;

    /**
     * 排序
     */
    private Double orderNum;


}