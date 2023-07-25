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
 * @date 2023-01-10 10:39:53.040
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
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long rateLimitLogId;

    /**
     * 被拦截请求IP
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String rateLimitLogIp;

    /**
     * 被拦截请求URI
     */
    @Size(max = 500, message = "{noMoreThan}")
    private String requestUri;

    /**
     * 被拦截请求方法
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String requestMethod;

    /**
     * 拦截时间
     */
    @NotNull(message = "{required}")
    private LocalDateTime requestTime;

    /**
     * IP对应地址
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String location;


}