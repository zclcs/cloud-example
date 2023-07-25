package com.zclcs.platform.system.api.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 限流规则 Entity
 *
 * @author zclcs
 * @date 2023-01-10 10:39:49.113
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_rate_limit_rule")
public class RateLimitRule extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流规则id
     */
    @TableId(value = "rate_limit_rule_id", type = IdType.AUTO)
    private Long rateLimitRuleId;

    /**
     * 请求uri（不支持通配符）
     */
    @TableField("request_uri")
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 限制时间起
     */
    @TableField("limit_from")
    private String limitFrom;

    /**
     * 限制时间止
     */
    @TableField("limit_to")
    private String limitTo;

    /**
     * 限制次数
     */
    @TableField("rate_limit_count")
    private Integer rateLimitCount;

    /**
     * 时间周期（单位秒）
     */
    @TableField("interval_sec")
    private String intervalSec;

    /**
     * 规则状态 默认 1 @@enable_disable
     */
    @TableField("rule_status")
    private String ruleStatus;


}