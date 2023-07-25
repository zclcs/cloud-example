package com.zclcs.platform.maintenance.bean.vo;

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

    /**
     * 服务名
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 服务名
     */
    @Schema(description = "执行器主键ID")
    private Integer jobGroup;

    /**
     * 服务名
     */
    @Schema(description = "任务，主键ID")
    private Integer jobId;

    /**
     * 服务名
     */
    @Schema(description = "执行器地址，本次执行的地址")
    private String executorAddress;

    /**
     * 服务名
     */
    @Schema(description = "执行器任务handler")
    private String executorHandler;

    /**
     * 服务名
     */
    @Schema(description = "执行器任务参数")
    private String executorParam;

    /**
     * 服务名
     */
    @Schema(description = "执行器任务分片参数，格式如 1/2")
    private String executorShardingParam;

    /**
     * 服务名
     */
    @Schema(description = "失败重试次数")
    private int executorFailRetryCount;

    /**
     * 服务名
     */
    @Schema(description = "调度时间")
    private String triggerTime;

    /**
     * 服务名
     */
    @Schema(description = "调度结果")
    private Integer triggerCode;

    /**
     * 服务名
     */
    @Schema(description = "调度日志")
    private String triggerMsg;

    /**
     * 服务名
     */
    @Schema(description = "执行时间")
    private String handleTime;

    /**
     * 服务名
     */
    @Schema(description = "执行状态")
    private Integer handleCode;

    /**
     * 服务名
     */
    @Schema(description = "执行日志")
    private String handleMsg;

    /**
     * 服务名
     */
    @Schema(description = "告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败")
    private Integer alarmStatus;
}
