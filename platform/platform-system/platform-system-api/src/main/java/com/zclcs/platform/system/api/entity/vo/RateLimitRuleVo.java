package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 限流规则 Vo
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
@Schema(name = "RateLimitRuleVo对象", description = "限流规则")
public class RateLimitRuleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "限流规则id")
    private Long rateLimitRuleId;

    @Schema(description = "请求uri（不支持通配符）")
    private String requestUri;

    @Schema(description = "请求方法，如果为ALL则表示对所有方法生效")
    private String requestMethod;

    @Schema(description = "限制时间起")
    private String limitFrom;

    @Schema(description = "限制时间止")
    private String limitTo;

    @Schema(description = "限制次数")
    private Integer rateLimitCount;

    @Schema(description = "时间周期（单位秒）")
    private String intervalSec;

    @Schema(description = "规则状态 默认 1 @@enable_disable")
//    @DictText("enable_disable")
    private String ruleStatus;


}