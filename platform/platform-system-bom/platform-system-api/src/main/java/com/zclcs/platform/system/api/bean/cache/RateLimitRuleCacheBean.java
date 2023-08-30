package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.platform.system.api.bean.entity.RateLimitRule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 限流规则 Entity
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RateLimitRuleCacheBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 请求uri（不支持通配符）
     */
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    private String requestMethod;

    /**
     * 限制时间起
     */
    private String limitFrom;

    /**
     * 限制时间止
     */
    private String limitTo;

    /**
     * 限制次数
     */
    private Integer rateLimitCount;

    /**
     * 时间周期（单位秒）
     */
    private String intervalSec;

    /**
     * 规则状态 默认 1 @@enable_disable
     */
    private String ruleStatus;


    public static RateLimitRuleCacheBean convertToRateLimitRuleCacheBean(RateLimitRule item) {
        if (item == null) {
            return null;
        }
        RateLimitRuleCacheBean result = new RateLimitRuleCacheBean();
        result.setRequestUri(item.getRequestUri());
        result.setRequestMethod(item.getRequestMethod());
        result.setLimitFrom(item.getLimitFrom());
        result.setLimitTo(item.getLimitTo());
        result.setRateLimitCount(item.getRateLimitCount());
        result.setIntervalSec(item.getIntervalSec());
        result.setRuleStatus(item.getRuleStatus());
        return result;
    }
}