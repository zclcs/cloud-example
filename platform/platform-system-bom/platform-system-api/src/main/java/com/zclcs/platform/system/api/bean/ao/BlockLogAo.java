package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 黑名单拦截日志 Ao
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
@Schema(title = "BlockLogAo对象", description = "黑名单拦截日志")
public class BlockLogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "拦截日志id")
    private Long blockId;

    @Size(max = 200, message = "{noMoreThan}")
    @Schema(description = "拦截ip")
    private String blockIp;

    @Size(max = 500, message = "{noMoreThan}")
    @Schema(description = "被拦截请求URI")
    private String requestUri;

    @Size(max = 200, message = "{noMoreThan}")
    @Schema(description = "被拦截请求方法")
    private String requestMethod;

    @NotNull(message = "{required}")
    @Schema(description = "拦截时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime requestTime;

    @Size(max = 200, message = "{noMoreThan}")
    @Schema(description = "IP对应地址")
    private String location;


}