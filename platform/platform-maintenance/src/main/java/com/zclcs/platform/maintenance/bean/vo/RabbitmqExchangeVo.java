package com.zclcs.platform.maintenance.bean.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Map;

/**
 * NacosTokenVo
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
@Schema(title = "RabbitmqExchangeVo", description = "RabbitmqExchangeVo")
public class RabbitmqExchangeVo {

    /**
     * 服务名
     */
    @Schema(description = "额外参数")
    private Map<String, Object> arguments;

    /**
     * 服务名
     */
    @Schema(description = "不使用是否自动删除")
    @JsonAlias("auto_delete")
    private String autoDelete;

    /**
     * 服务名
     */
    @Schema(description = "是否持久化")
    private String durable;

    /**
     * 服务名
     */
    @Schema(description = "是否内部交换")
    private String internal;

    /**
     * 服务名
     */
    @Schema(description = "消息统计")
    @JsonAlias("message_stats")
    private MessageStats messageStats;

    /**
     * 服务名
     */
    @Schema(description = "交换机名称")
    private String name;

    /**
     * 服务名
     */
    @Schema(description = "交换机类型")
    private String type;

    /**
     * 服务名
     */
    @Schema(description = "权限")
    @JsonAlias("user_who_performed_action")
    private String userWhoPerformedAction;

    /**
     * 服务名
     */
    @Schema(description = "命名空间")
    private String vhost;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "MessageStats", description = "MessageStats")
    public static class MessageStats {

        /**
         * 服务名
         */
        @Schema(description = "入")
        @JsonAlias("publish_in")
        private Long publishIn;

        /**
         * 服务名
         */
        @Schema(description = "入详细")
        @JsonAlias("publish_in_details")
        private MessageStatsDetail publishInDetails;

        /**
         * 服务名
         */
        @Schema(description = "出")
        @JsonAlias("publish_out")
        private Long publishOut;

        /**
         * 服务名
         */
        @Schema(description = "出详细")
        @JsonAlias("publish_out_details")
        private MessageStatsDetail publishOutDetails;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "MessageStatsDetail", description = "MessageStatsDetail")
    public static class MessageStatsDetail {

        /**
         * 服务名
         */
        @Schema(description = "入 百分比 /s")
        private BigDecimal rate;
    }
}
