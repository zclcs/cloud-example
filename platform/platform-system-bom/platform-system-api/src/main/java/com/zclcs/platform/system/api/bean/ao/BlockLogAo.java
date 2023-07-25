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
 * 黑名单拦截日志 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:40:05.798
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlockLogAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 拦截日志id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long blockId;

    /**
     * 拦截ip
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String blockIp;

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