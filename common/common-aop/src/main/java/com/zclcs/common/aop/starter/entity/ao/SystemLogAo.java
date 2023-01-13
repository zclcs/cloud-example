package com.zclcs.common.aop.starter.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 用户操作日志表
 * </p>
 *
 * @author zclcs
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "用户操作日志表")
public class SystemLogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "日志id")
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long id;

    @Schema(description = "类名")
    private String className;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "操作用户")
    private String username;

    @Schema(description = "操作内容")
    private String operation;

    @Schema(description = "耗时")
    private BigDecimal time;

    @Schema(description = "操作方法")
    private String method;

    @Schema(description = "方法参数")
    private String params;

    @Schema(description = "操作者ip")
    private String ip;

    @Schema(description = "方法开始时间")
    private Long start;

    @Schema(description = "操作地点")
    private String location;

    @Schema(description = "创建时间-开始")
    private LocalDate createTimeFrom;

    @Schema(description = "创建时间-结束")
    private LocalDate createTimeTo;
}
