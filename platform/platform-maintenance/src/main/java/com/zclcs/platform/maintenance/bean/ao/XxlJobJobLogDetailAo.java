package com.zclcs.platform.maintenance.bean.ao;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 查询日志 Ao
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class XxlJobJobLogDetailAo {

    /**
     * 日志id
     */
    @NotNull(message = "{required}")
    private Long logId;

}
