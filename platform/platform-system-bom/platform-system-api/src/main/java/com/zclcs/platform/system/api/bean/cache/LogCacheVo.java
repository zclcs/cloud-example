package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户操作日志 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:55:02.695
 */
@Data
public class LogCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    private Long id;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 耗时
     */
    private BigDecimal time;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 方法参数
     */
    private String params;

    /**
     * 操作者ip
     */
    private String ip;

    /**
     * 操作地点
     */
    private String location;


}