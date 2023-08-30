package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * XxlJobJobLogVo
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
public class XxlJobJobLogVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 执行器主键ID
     */
    private Integer jobGroup;

    /**
     * 任务，主键ID
     */
    private Integer jobId;

    /**
     * 执行器地址，本次执行的地址
     */
    private String executorAddress;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    private String executorParam;

    /**
     * 执行器任务分片参数，格式如 1/2
     */
    private String executorShardingParam;

    /**
     * 失败重试次数
     */
    private int executorFailRetryCount;

    /**
     * 调度时间
     */
    private String triggerTime;

    /**
     * 调度结果
     */
    private Integer triggerCode;

    /**
     * 调度日志
     */
    private String triggerMsg;

    /**
     * 执行时间
     */
    private String handleTime;

    /**
     * 执行状态
     */
    private Integer handleCode;

    /**
     * 执行日志
     */
    private String handleMsg;

    /**
     * 告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败
     */
    private Integer alarmStatus;
}
