package com.zclcs.platform.maintenance.bean.vo;

import com.zclcs.platform.maintenance.bean.enums.LogLevel;
import com.zclcs.platform.maintenance.bean.enums.LogType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 任务日志配置
 *
 * @author yhz
 * @since 2022/9/16
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PowerJobLogConfigVo {
    /**
     * log type {@link LogType}
     */
    private Integer type;
    /**
     * log level {@link LogLevel}
     */
    private Integer level;

    private String loggerName;


}
