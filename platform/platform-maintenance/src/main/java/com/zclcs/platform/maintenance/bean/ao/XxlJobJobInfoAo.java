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
public class XxlJobJobInfoAo {

    /**
     * 调度状态：0-停止，1-运行，-1-全部
     */
    private Integer triggerStatus;

    /**
     * 描述
     */
    private String jobDesc;

    /**
     * 执行器，任务Handler名称
     */
    private String executorHandler;

    /**
     * 负责人
     */
    private String author;

}
