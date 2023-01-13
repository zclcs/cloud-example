package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 限流规则 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "RateLimitRuleAo对象", description = "限流规则")
public class RateLimitRuleAo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "限流规则id")
    private Long rateLimitRuleId;

    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "请求uri（不支持通配符）", required = true)
    private String requestUri;

    @Size(max = 10, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "请求方法，如果为ALL则表示对所有方法生效", required = true)
    private String requestMethod;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(description = "限制时间起")
    private String limitFrom;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(description = "限制时间止")
    private String limitTo;

    @NotNull(message = "{required}")
    @Schema(description = "限制次数", required = true)
    private Integer rateLimitCount;

    @Size(max = 20, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "时间周期（单位秒）", required = true)
    private String intervalSec;

    //    @DictValid(value = "enable_disable", message = "{dict}")
    @Size(max = 40, message = "{noMoreThan}")
    @Schema(description = "规则状态 默认 1 @@enable_disable")
    private String ruleStatus;


}