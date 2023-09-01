package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 限流拦截日志 Ao
 *
 * @author zclcs
 * @since 2023-09-01 19:53:54.652
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RateLimitLogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流日志id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long rateLimitLogId;

    /**
     * 被拦截请求IP
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String rateLimitLogIp;

    /**
     * 被拦截请求URI
     * 默认值：
     */
    @Size(max = 500, message = "{noMoreThan}")
    private String requestUri;

    /**
     * 被拦截请求方法
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String requestMethod;

    /**
     * 拦截时间
     * 默认值：CURRENT_TIMESTAMP
     */
    @NotNull(message = "{required}")
    private LocalDateTime requestTime;

    /**
     * IP对应地址
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String location;


}