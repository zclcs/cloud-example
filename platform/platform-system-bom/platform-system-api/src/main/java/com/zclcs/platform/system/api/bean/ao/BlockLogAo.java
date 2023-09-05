package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
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
 * @since 2023-09-01 19:53:49.983
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
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long blockId;

    /**
     * 拦截ip
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String blockIp;

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