package com.zclcs.platform.system.api.entity.vo;

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
@Schema(title = "XxlJobJobInfoVo对象", description = "XxlJobJobLogVo")
public class XxlJobJobLogVo {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "执行器主键ID")
    private Integer jobGroup;

    @Schema(description = "任务，主键ID")
    private Integer jobId;

    @Schema(description = "执行器地址，本次执行的地址")
    private String executorAddress;

    @Schema(description = "执行器任务handler")
    private String executorHandler;

    @Schema(description = "执行器任务参数")
    private String executorParam;

    @Schema(description = "执行器任务分片参数，格式如 1/2")
    private String executorShardingParam;

    @Schema(description = "失败重试次数")
    private int executorFailRetryCount;

    @Schema(description = "调度时间")
    private String triggerTime;

    @Schema(description = "调度结果")
    private Integer triggerCode;

    @Schema(description = "调度日志")
    private String triggerMsg;

    @Schema(description = "执行时间")
    private String handleTime;

    @Schema(description = "执行状态")
    private Integer handleCode;

    @Schema(description = "执行日志")
    private String handleMsg;

    @Schema(description = "告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败")
    private Integer alarmStatus;
}
