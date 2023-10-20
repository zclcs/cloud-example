package com.zclcs.platform.maintenance.bean.vo;

import lombok.Data;

/**
 * InstanceInfo 对外展示对象
 *
 * @author tjq
 * @since 2020/4/12
 */
@Data
public class PowerJobInstanceInfoVo {

    /**
     * 任务ID（JS精度丢失）
     */
    private String jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务实例ID（JS精度丢失）
     */
    private String instanceId;
    /**
     * 该任务实例所属的 workflow ID，仅 workflow 任务存在
     */
    private String wfInstanceId;
    /**
     * 执行结果
     */
    private String result;
    /**
     * TaskTracker地址
     */
    private String taskTrackerAddress;
    /**
     * 总共执行的次数（用于重试判断）
     */
    private Long runningTimes;
    private int status;

    /* ********** 不一致区域 ********** */
    /**
     * 实际触发时间（需要格式化为人看得懂的时间）
     */
    private String actualTriggerTime;
    /**
     * 结束时间（同理，需要格式化）
     */
    private String finishedTime;
}
