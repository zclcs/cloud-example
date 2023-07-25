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
@Schema(title = "XxlJobJobInfoVo对象", description = "XxlJobJobInfoVo")
public class XxlJobJobInfoVo {

    /**
     * 服务名
     */
    @Schema(description = "主键ID")
    private Integer id;

    /**
     * 服务名
     */
    @Schema(description = "执行器主键ID")
    private Integer jobGroup;

    /**
     * 服务名
     */
    @Schema(description = "描述")
    private String jobDesc;

    /**
     * 服务名
     */
    @Schema(description = "创建时间")
    private String addTime;

    /**
     * 服务名
     */
    @Schema(description = "更新时间")
    private String updateTime;

    /**
     * 服务名
     */
    @Schema(description = "负责人")
    private String author;

    /**
     * 服务名
     */
    @Schema(description = "报警邮件")
    private String alarmEmail;

    /**
     * 服务名
     */
    @Schema(description = "调度类型")
    private String scheduleType;

    /**
     * 服务名
     */
    @Schema(description = "调度配置，值含义取决于调度类型")
    private String scheduleConf;

    /**
     * 服务名
     */
    @Schema(description = "调度过期策略")
    private String misfireStrategy;

    /**
     * 服务名
     */
    @Schema(description = "执行器路由策略")
    private String executorRouteStrategy;

    /**
     * 服务名
     */
    @Schema(description = "执行器，任务Handler名称")
    private String executorHandler;

    /**
     * 服务名
     */
    @Schema(description = "执行器，任务参数")
    private String executorParam;

    /**
     * 服务名
     */
    @Schema(description = "阻塞处理策略")
    private String executorBlockStrategy;

    /**
     * 服务名
     */
    @Schema(description = "任务执行超时时间，单位秒")
    private Integer executorTimeout;

    /**
     * 服务名
     */
    @Schema(description = "失败重试次数")
    private Integer executorFailRetryCount;

    /**
     * 服务名
     */
    @Schema(description = "GLUE类型 #com.xxl.job.core.glue.GlueTypeEnum")
    private String glueType;

    /**
     * 服务名
     */
    @Schema(description = "GLUE源代码")
    private String glueSource;

    /**
     * 服务名
     */
    @Schema(description = "GLUE备注")
    private String glueRemark;

    /**
     * 服务名
     */
    @Schema(description = "GLUE更新时间")
    private String glueUpdatetime;

    /**
     * 服务名
     */
    @Schema(description = "子任务ID，多个逗号分隔")
    private String childJobId;

    /**
     * 服务名
     */
    @Schema(description = "调度状态：0-停止，1-运行")
    private Integer triggerStatus;

    /**
     * 服务名
     */
    @Schema(description = "上次调度时间")
    private Long triggerLastTime;

    /**
     * 服务名
     */
    @Schema(description = "下次调度时间")
    private Long triggerNextTime;
}
