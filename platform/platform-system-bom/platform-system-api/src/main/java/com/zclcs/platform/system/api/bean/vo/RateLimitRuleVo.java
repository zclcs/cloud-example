package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 限流规则 Vo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:43.828
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RateLimitRuleVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流规则id
     * 默认值：
     */
    private Long rateLimitRuleId;

    /**
     * 请求uri（不支持通配符）
     * 默认值：
     */
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     * 默认值：
     */
    private String requestMethod;

    /**
     * 限制时间起
     * 默认值：00:00:00
     */
    private String limitFrom;

    /**
     * 限制时间止
     * 默认值：23:59:59
     */
    private String limitTo;

    /**
     * 限制次数
     * 默认值：1
     */
    private Integer rateLimitCount;

    /**
     * 时间周期（单位秒）
     * 默认值：1
     */
    private String intervalSec;

    /**
     * 规则状态 默认 1 @@enable_disable
     * 默认值：1
     */
    private String ruleStatus;

    /**
     * 规则状态 默认 1 @@enable_disable
     */
    private String ruleStatusText;

    public String getRuleStatusText() {
        return DictCacheUtil.getDictTitle("enable_disable", this.ruleStatus);
    }


}