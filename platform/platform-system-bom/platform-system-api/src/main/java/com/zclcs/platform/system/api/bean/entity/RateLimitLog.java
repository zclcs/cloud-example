package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 限流拦截日志 Entity
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
@Table("system_rate_limit_log")
public class RateLimitLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流日志id
     */
    @Id(keyType = KeyType.Auto)
    private Long rateLimitLogId;

    /**
     * 被拦截请求IP
     */
    @Column("rate_limit_log_ip")
    private String rateLimitLogIp;

    /**
     * 被拦截请求URI
     */
    @Column("request_uri")
    private String requestUri;

    /**
     * 被拦截请求方法
     */
    @Column("request_method")
    private String requestMethod;

    /**
     * 拦截时间
     */
    @Column("request_time")
    private LocalDateTime requestTime;

    /**
     * IP对应地址
     */
    @Column("location")
    private String location;


}