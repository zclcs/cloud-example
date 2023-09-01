package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 限流规则 Ao
 *
 * @author zclcs
 * @since 2023-09-01 19:53:43.828
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RateLimitRuleAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流规则id
     * 默认值：
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long rateLimitRuleId;

    /**
     * 请求uri（不支持通配符）
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     * 默认值：
     */
    @Size(max = 10, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String requestMethod;

    /**
     * 限制时间起
     * 默认值：00:00:00
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String limitFrom;

    /**
     * 限制时间止
     * 默认值：23:59:59
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String limitTo;

    /**
     * 限制次数
     * 默认值：1
     */
    @NotNull(message = "{required}")
    private Integer rateLimitCount;

    /**
     * 时间周期（单位秒）
     * 默认值：1
     */
    @Size(max = 20, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String intervalSec;

    /**
     * 规则状态 默认 1 @@enable_disable
     * 默认值：1
     */
    @DictValid(value = "enable_disable", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    private String ruleStatus;


}