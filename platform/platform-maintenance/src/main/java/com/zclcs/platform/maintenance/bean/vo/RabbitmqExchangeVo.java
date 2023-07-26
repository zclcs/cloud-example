package com.zclcs.platform.maintenance.bean.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Map;

/**
 * RabbitmqExchangeVo
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
public class RabbitmqExchangeVo {

    /**
     * 额外参数
     */
    private Map<String, Object> arguments;

    /**
     * 不使用是否自动删除
     */
    @JsonAlias("auto_delete")
    private String autoDelete;

    /**
     * 是否持久化
     */
    private String durable;

    /**
     * 是否内部交换
     */
    private String internal;

    /**
     * 消息统计
     */
    @JsonAlias("message_stats")
    private MessageStats messageStats;

    /**
     * 交换机名称
     */
    private String name;

    /**
     * 交换机类型
     */
    private String type;

    /**
     * 权限
     */
    @JsonAlias("user_who_performed_action")
    private String userWhoPerformedAction;

    /**
     * 命名空间
     */
    private String vhost;

    /**
     * 消息统计详情
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class MessageStats {

        /**
         * 入
         */
        @JsonAlias("publish_in")
        private Long publishIn;

        /**
         * 入详细
         */
        @JsonAlias("publish_in_details")
        private MessageStatsDetail publishInDetails;

        /**
         * 出
         */
        @JsonAlias("publish_out")
        private Long publishOut;

        /**
         * 出详细
         */
        @JsonAlias("publish_out_details")
        private MessageStatsDetail publishOutDetails;
    }

    /**
     * MessageStatsDetail
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class MessageStatsDetail {

        /**
         * 入 百分比 /s
         */
        private BigDecimal rate;
    }
}
