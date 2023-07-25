package com.zclcs.platform.maintenance.bean.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
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
public class RabbitmqQueueVo {

    /**
     * 服务名
     */
    @Schema(description = "额外参数")
    private Map<String, String> arguments;

    /**
     * 服务名
     */
    @Schema(description = "不使用是否自动删除")
    @JsonAlias("auto_delete")
    private String autoDelete;

    /**
     * 服务名
     */
    @Schema(description = "队列状态")
    @JsonAlias("backing_queue_status")
    private BackingQueueStatus backingQueueStatus;

    /**
     * 服务名
     */
    @Schema(description = "消费能力")
    @JsonAlias("consumer_capacity")
    private Long consumerCapacity;

    /**
     * 服务名
     */
    @Schema(description = "消费者使用")
    @JsonAlias("consumer_utilisation")
    private Long consumerUtilisation;

    /**
     * 服务名
     */
    @Schema(description = "消费者")
    private Long consumers;

    /**
     * 服务名
     */
    @Schema(description = "是否持久化")
    private String durable;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("effective_policy_definition")
    private Object effectivePolicyDefinition;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    private String exclusive;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("exclusive_consumer_tag")
    private String exclusiveConsumerTag;

    /**
     * 服务名
     */
    @Schema(description = "不明 连接数统计？")
    @JsonAlias("garbage_collection")
    private GarbageCollection garbageCollection;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("head_message_timestamp")
    private String headMessageTimestamp;

    /**
     * 服务名
     */
    @Schema(description = "最后时间")
    @JsonAlias("idle_since")
    private String idleSince;

    /**
     * 服务名
     */
    @Schema(description = "内存用量")
    private Long memory;

    /**
     * 服务名
     */
    @Schema(description = "消息字节数")
    @JsonAlias("message_bytes")
    private Long messageBytes;

    /**
     * 服务名
     */
    @Schema(description = "消息字节数-出")
    @JsonAlias("message_bytes_paged_out")
    private Long messageBytesPagedOut;

    /**
     * 服务名
     */
    @Schema(description = "消息字节数-?")
    @JsonAlias("message_bytes_persistent")
    private Long messageBytesPersistent;

    /**
     * 服务名
     */
    @Schema(description = "消息字节数-?")
    @JsonAlias("message_bytes_ram")
    private Long messageBytesRam;

    /**
     * 服务名
     */
    @Schema(description = "消息字节数-已入队")
    @JsonAlias("message_bytes_ready")
    private Long messageBytesReady;

    /**
     * 服务名
     */
    @Schema(description = "消息字节数-未ack")
    @JsonAlias("message_bytes_unacknowledged")
    private Long messageBytesUnacknowledged;

    /**
     * 服务名
     */
    @Schema(description = "消息统计")
    @JsonAlias("message_stats")
    private MessageStats messageStats;

    /**
     * 服务名
     */
    @Schema(description = "消息")
    private Long messages;

    /**
     * 服务名
     */
    @Schema(description = "消息详情")
    @JsonAlias("messages_details")
    private MessageStatsDetail messagesDetails;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("messages_paged_out")
    private Long messagesPagedOut;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("messages_persistent")
    private Long messagesPersistent;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("messages_ram")
    private Long messagesRam;

    /**
     * 服务名
     */
    @Schema(description = "已入队")
    @JsonAlias("messages_ready")
    private Long messagesReady;

    /**
     * 服务名
     */
    @Schema(description = "已入队详情")
    @JsonAlias("messages_ready_details")
    private MessageStatsDetail messagesReadyDetails;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("messages_ready_ram")
    private Long messagesReadyRam;

    /**
     * 服务名
     */
    @Schema(description = "未消费")
    @JsonAlias("messages_unacknowledged")
    private Long messagesUnacknowledged;

    /**
     * 服务名
     */
    @Schema(description = "未消费详情")
    @JsonAlias("messages_unacknowledged_details")
    private MessageStatsDetail messagesUnacknowledgedDetails;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("messages_unacknowledged_ram")
    private Long messagesUnacknowledgedRam;

    /**
     * 服务名
     */
    @Schema(description = "队列名称")
    private String name;

    /**
     * 服务名
     */
    @Schema(description = "节点")
    private String node;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("operator_policy")
    private String operatorPolicy;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    private String policy;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("recoverable_slaves")
    private String recoverableSlaves;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    private Long reductions;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("reductions_details")
    private MessageStatsDetail reductionsDetails;

    /**
     * 服务名
     */
    @Schema(description = "不明")
    @JsonAlias("single_active_consumer_tag")
    private String singleActiveConsumerTag;

    /**
     * 服务名
     */
    @Schema(description = "状态")
    private String state;

    /**
     * 服务名
     */
    @Schema(description = "类型")
    private String type;

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
    @Schema(title = "BackingQueueStatus", description = "BackingQueueStatus")
    public static class BackingQueueStatus {

        /**
         * 服务名
         */
        @Schema(description = "平均消费-出")
        @JsonAlias("avg_ack_egress_rate")
        private BigDecimal avgAckEgressRate;

        /**
         * 服务名
         */
        @Schema(description = "平均消费-进")
        @JsonAlias("avg_ack_ingress_rate")
        private BigDecimal avgAckIngressRate;

        /**
         * 服务名
         */
        @Schema(description = "平均-出")
        @JsonAlias("avg_egress_rate")
        private BigDecimal avgEgressRate;

        /**
         * 服务名
         */
        @Schema(description = "平均-进")
        @JsonAlias("avg_ingress_rate")
        private BigDecimal avgIngressRate;

        /**
         * 服务名
         */
        @Schema(description = "意义不明")
        private List<String> delta;

        /**
         * 服务名
         */
        @Schema(description = "长度")
        private Long len;

        /**
         * 服务名
         */
        @Schema(description = "模式")
        private String mode;

        /**
         * 服务名
         */
        @Schema(description = "下一个序列号")
        @JsonAlias("next_seq_id")
        private Long nextSeqId;

        /**
         * 服务名
         */
        @Schema(description = "意义不明")
        private Long q1;

        /**
         * 服务名
         */
        @Schema(description = "意义不明")
        private Long q2;

        /**
         * 服务名
         */
        @Schema(description = "意义不明")
        private Long q3;

        /**
         * 服务名
         */
        @Schema(description = "意义不明")
        private Long q4;

        /**
         * 服务名
         */
        @Schema(description = "意义不明")
        @JsonAlias("target_ram_count")
        private String targetRamCount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "GarbageCollection", description = "GarbageCollection")
    public static class GarbageCollection {

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("fullsweep_after")
        private Long fullsweepAfter;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("max_heap_size")
        private Long maxHeapSize;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("min_bin_vheap_size")
        private Long minBinVheapSize;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("min_heap_size")
        private Long minHeapSize;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("minor_gcs")
        private Long minorGcs;

    }

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
        @Schema(description = "消费")
        private Long ack;

        /**
         * 服务名
         */
        @Schema(description = "消费详情")
        @JsonAlias("ack_details")
        private MessageStatsDetail ackDetails;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        private Long deliver;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("deliver_details")
        private MessageStatsDetail deliverDetails;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("deliver_get")
        private Long deliverGet;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("deliver_get_details")
        private MessageStatsDetail deliverGetDetails;

        /**
         * 服务名
         */
        @Schema(description = "未消费")
        @JsonAlias("deliver_no_ack")
        private Long deliverNoAck;

        /**
         * 服务名
         */
        @Schema(description = "未消费详情")
        @JsonAlias("deliver_no_ack_details")
        private MessageStatsDetail deliverNoAckDetails;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        private Long get;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("get_details")
        private MessageStatsDetail getDetails;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("get_empty")
        private Long getEmpty;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("get_empty_details")
        private MessageStatsDetail getEmptyDetails;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("get_no_ack")
        private Long getNoAck;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("get_no_ack_details")
        private MessageStatsDetail getNoAckDetails;

        /**
         * 服务名
         */
        @Schema(description = "推送")
        private Long publish;

        /**
         * 服务名
         */
        @Schema(description = "推送详情")
        @JsonAlias("publish_details")
        private MessageStatsDetail publishDetails;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        private Long redeliver;

        /**
         * 服务名
         */
        @Schema(description = "不明")
        @JsonAlias("redeliver_details")
        private MessageStatsDetail redeliverDetails;
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "Arguments", description = "Arguments")
    public static class Arguments {

        /**
         * 服务名
         */
        @Schema(description = "死信交换机")
        @JsonAlias("x-dead-letter-exchange")
        private String xDeadLetterExchange;

        /**
         * 服务名
         */
        @Schema(description = "死信路由key")
        @JsonAlias("x-dead-letter-routing-key")
        private String xDeadLetterRoutingKey;

        /**
         * 服务名
         */
        @Schema(description = "最大存活时间")
        @JsonAlias("x-message-ttl")
        private String xMessageTtl;
    }
}
