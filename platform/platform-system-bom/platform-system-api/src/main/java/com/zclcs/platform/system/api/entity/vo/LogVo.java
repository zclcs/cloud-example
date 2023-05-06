package com.zclcs.platform.system.api.entity.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 用户操作日志 Vo
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
@Schema(title = "LogVo对象", description = "用户操作日志")
public class LogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "日志id")
    private Long id;

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

    @Schema(description = "操作地点")
    private String location;

    @Schema(description = "创建时间-开始")
    private LocalDate createAtFrom;

    @Schema(description = "创建时间-结束")
    private LocalDate createAtTo;


}