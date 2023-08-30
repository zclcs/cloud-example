package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * XxlJobJobLogDetailVo
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
public class XxlJobJobLogDetailVo {

    /**
     * 开始行
     */
    private Integer fromLineNum;

    /**
     * 结束行
     */
    private Integer toLineNum;

    /**
     * 日志
     */
    private String logContent;

    /**
     * 是否结束
     */
    private Boolean end;

}
