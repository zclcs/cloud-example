package com.zclcs.platform.system.api.bean.ao;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class XxlJobJobLogAo {

    @Schema(description = "任务执行器id")
    private Integer jobGroup;

    @Schema(description = "任务id")
    private Integer jobId;

    @Schema(description = "日志状态：0-失败，1-成功，-1-全部")
    private Integer logStatus;

    @Schema(description = "时间开始")
    private String filterTimeFrom;

    @Schema(description = "时间结束")
    private String filterTimeTo;

}
