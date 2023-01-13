package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 黑名单拦截日志 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:40:05.798
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "BlockLogVo对象", description = "黑名单拦截日志")
public class BlockLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "拦截日志id")
    private Long blockId;

    @Schema(description = "拦截ip")
    private String blockIp;

    @Schema(description = "被拦截请求URI")
    private String requestUri;

    @Schema(description = "被拦截请求方法")
    private String requestMethod;

    @Schema(description = "IP对应地址")
    private String location;

    @Schema(description = "创建时间起")
    private LocalDate createTimeFrom;

    @Schema(description = "创建时间起终")
    private LocalDate createTimeTo;


}