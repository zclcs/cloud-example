package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 限流拦截日志 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:54.652
 */
@Data
public class RateLimitLogCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流日志id
     */
    private Long rateLimitLogId;

    /**
     * 被拦截请求IP
     */
    private String rateLimitLogIp;

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