package com.zclcs.platform.maintenance.bean.ao;

import com.zclcs.platform.maintenance.bean.enums.InstanceStatus;
import com.zclcs.platform.maintenance.bean.enums.InstanceType;
import lombok.Data;

/**
 * 任务实例查询对象
 *
 * @author tjq
 * @since 2020/4/14
 */
@Data
public class PowerJobQueryInstanceAo {

    /**
     * 任务所属应用ID
     */
    private Long appId;
    /**
     * 查询条件（NORMAL/WORKFLOW）
     * {@link InstanceType}
     */
    private InstanceType type;

    private Long instanceId;

    private Long jobId;

    private Long wfInstanceId;

    /**
     * 任务实例状态
     * {@link InstanceStatus}
     */
    private String status;
}
