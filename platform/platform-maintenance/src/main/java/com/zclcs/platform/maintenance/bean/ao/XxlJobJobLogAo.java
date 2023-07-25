package com.zclcs.platform.maintenance.bean.ao;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 任务管理 Ao
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
public class XxlJobJobLogAo {

    /**
     * 任务执行器id
     */
    private Integer jobGroup;

    /**
     * 任务id
     */
    private Integer jobId;

    /**
     * 日志状态：0-失败，1-成功，-1-全部
     */
    private Integer logStatus;

    /**
     * 时间开始
     */
    private String filterTimeFrom;

    /**
     * 时间结束
     */
    private String filterTimeTo;

}
