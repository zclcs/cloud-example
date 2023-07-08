package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 限流拦截日志 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:53.040
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "RateLimitLogVo对象", description = "限流拦截日志")
public class RateLimitLogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "限流日志id")
    private Long rateLimitLogId;

    @Schema(description = "被拦截请求IP")
    private String rateLimitLogIp;

    @Schema(description = "被拦截请求URI")
    private String requestUri;

    @Schema(description = "被拦截请求方法")
    private String requestMethod;

    @Schema(description = "IP对应地址")
    private String location;

    @Schema(description = "拦截时间")
    private LocalDateTime requestTime;

    @Schema(description = "拦截时间起")
    private LocalDate requestTimeFrom;

    @Schema(description = "拦截时间起终")
    private LocalDate requestTimeTo;


}