package com.zclcs.platform.maintenance.bean.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * NacosTokenVo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "XxlJobJobLogAo", description = "XxlJobJobLogAo")
public class XxlJobJobLogDetailAo {

    @Schema(description = "日志id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "{required}")
    private Long logId;

}
