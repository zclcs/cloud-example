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
@Schema(title = "XxlJobJobInfoAo对象", description = "XxlJobJobInfoAo")
public class XxlJobJobInfoAo {

    @Schema(description = "调度状态：0-停止，1-运行，-1-全部")
    private Integer triggerStatus;

    @Schema(description = "描述")
    private String jobDesc;

    @Schema(description = "执行器，任务Handler名称")
    private String executorHandler;

    @Schema(description = "负责人")
    private String author;

}
