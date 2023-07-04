package com.zclcs.platform.system.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 限流拦截日志 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:39:53.040
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_rate_limit_log")
@Schema(title = "RateLimitLog对象", description = "限流拦截日志")
public class RateLimitLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流日志id
     */
    @TableId(value = "rate_limit_log_id", type = IdType.AUTO)
    private Long rateLimitLogId;

    /**
     * 被拦截请求IP
     */
    @TableField("rate_limit_log_ip")
    private String rateLimitLogIp;

    /**
     * 被拦截请求URI
     */
    @TableField("request_uri")
    private String requestUri;

    /**
     * 被拦截请求方法
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 拦截时间
     */
    @TableField("request_time")
    private String requestTime;

    /**
     * IP对应地址
     */
    @TableField("location")
    private String location;


}