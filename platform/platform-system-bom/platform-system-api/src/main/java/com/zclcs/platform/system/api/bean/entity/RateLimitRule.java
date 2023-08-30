package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 限流规则 Entity
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_rate_limit_rule")
public class RateLimitRule extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 限流规则id
     */
    @Id(value = "rate_limit_rule_id", keyType = KeyType.Auto)
    private Long rateLimitRuleId;

    /**
     * 请求uri（不支持通配符）
     */
    @Column("request_uri")
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    @Column("request_method")
    private String requestMethod;

    /**
     * 限制时间起
     */
    @Column("limit_from")
    private String limitFrom;

    /**
     * 限制时间止
     */
    @Column("limit_to")
    private String limitTo;

    /**
     * 限制次数
     */
    @Column("rate_limit_count")
    private Integer rateLimitCount;

    /**
     * 时间周期（单位秒）
     */
    @Column("interval_sec")
    private String intervalSec;

    /**
     * 规则状态 默认 1 @@enable_disable
     */
    @Column("rule_status")
    private String ruleStatus;


}