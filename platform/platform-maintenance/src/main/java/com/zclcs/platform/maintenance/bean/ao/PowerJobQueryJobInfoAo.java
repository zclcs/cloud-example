package com.zclcs.platform.maintenance.bean.ao;

import lombok.Data;

/**
 * 查询任务列表
 *
 * @author tjq
 * @since 2020/4/13
 */
@Data
public class PowerJobQueryJobInfoAo {

    /**
     * 任务所属应用ID
     */
    private Long appId;

    /**
     * 任务ID
     */
    private Long jobId;

    /**
     * 任务名称
     */
    private String keyword;
}
