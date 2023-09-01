package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 黑名单拦截日志 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:49.983
 */
@Data
public class BlockLogCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 拦截日志id
     */
    private Long blockId;

    /**
     * 拦截ip
     */
    private String blockIp;

    /**
     * 被拦截请求URI
     */
    private String requestUri;

    /**
     * 被拦截请求方法
     */
    private String requestMethod;

    /**
     * 拦截时间
     */
    private LocalDateTime requestTime;

    /**
     * IP对应地址
     */
    private String location;


}