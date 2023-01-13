package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户操作日志 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "LogAo对象", description = "用户操作日志")
public class LogAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "日志id")
    private Long id;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "操作用户")
    private String username;

    @Size(max = 65535, message = "{noMoreThan}")
    @Schema(description = "操作内容")
    private String operation;

    @Schema(description = "耗时")
    private BigDecimal time;

    @Size(max = 65535, message = "{noMoreThan}")
    @Schema(description = "操作方法")
    private String method;

    @Size(max = 65535, message = "{noMoreThan}")
    @Schema(description = "方法参数")
    private String params;

    @Size(max = 64, message = "{noMoreThan}")
    @Schema(description = "操作者ip")
    private String ip;

    @Size(max = 50, message = "{noMoreThan}")
    @Schema(description = "操作地点")
    private String location;


}