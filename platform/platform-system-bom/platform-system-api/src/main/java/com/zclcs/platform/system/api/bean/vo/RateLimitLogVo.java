package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 限流拦截日志 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:54.652
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RateLimitLogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流日志id
     * 默认值：
     */
    private Long rateLimitLogId;

    /**
     * 被拦截请求IP
     * 默认值：
     */
    private String rateLimitLogIp;

    /**
     * 被拦截请求URI
     * 默认值：
     */
    private String requestUri;

    /**
     * 被拦截请求方法
     * 默认值：
     */
    private String requestMethod;

    /**
     * 拦截时间
     * 默认值：CURRENT_TIMESTAMP
     */
    private LocalDateTime requestTime;

    /**
     * IP对应地址
     * 默认值：
     */
    private String location;

    /**
     * 拦截时间起
     */
    private LocalDate requestTimeFrom;

    /**
     * 拦截时间起终
     */
    private LocalDate requestTimeTo;


}