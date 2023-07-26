package com.zclcs.platform.maintenance.bean.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * RabbitmqQueueVo
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
public class RabbitmqQueueVo {

    /**
     * 额外参数
     */
    private Map<String, String> arguments;

    /**
     * 不使用是否自动删除
     */
    @JsonAlias("auto_delete")
    private String autoDelete;

    /**
     * 队列状态
     */
    @JsonAlias("backing_queue_status")
    private BackingQueueStatus backingQueueStatus;

    /**
     * 消费能力
     */
    @JsonAlias("consumer_capacity")
    private Long consumerCapacity;

    /**
     * 消费者使用
     */
    @JsonAlias("consumer_utilisation")
    private Long consumerUtilisation;

    /**
     * 消费者
     */
    private Long consumers;

    /**
     * 是否持久化
     */
    private String durable;

    /**
     * 不明
     */
    @JsonAlias("effective_policy_definition")
    private Object effectivePolicyDefinition;

    /**
     * 不明
     */
    private String exclusive;

    /**
     * 不明
     */
    @JsonAlias("exclusive_consumer_tag")
    private String exclusiveConsumerTag;

    /**
     * 不明 连接数统计？
     */
    @JsonAlias("garbage_collection")
    private GarbageCollection garbageCollection;

    /**
     * 不明
     */
    @JsonAlias("head_message_timestamp")
    private String headMessageTimestamp;

    /**
     * 最后时间
     */
    @JsonAlias("idle_since")
    private String idleSince;

    /**
     * 内存用量
     */
    private Long memory;

    /**
     * 消息字节数
     */
    @JsonAlias("message_bytes")
    private Long messageBytes;

    /**
     * 消息字节数-出
     */
    @JsonAlias("message_bytes_paged_out")
    private Long messageBytesPagedOut;

    /**
     * 消息字节数-?
     */
    @JsonAlias("message_bytes_persistent")
    private Long messageBytesPersistent;

    /**
     * 消息字节数-?
     */
    @JsonAlias("message_bytes_ram")
    private Long messageBytesRam;

    /**
     * 消息字节数-已入队
     */
    @JsonAlias("message_bytes_ready")
    private Long messageBytesReady;

    /**
     * 消息字节数-未ack
     */
    @JsonAlias("message_bytes_unacknowledged")
    private Long messageBytesUnacknowledged;

    /**
     * 消息统计
     */
    @JsonAlias("message_stats")
    private MessageStats messageStats;

    /**
     * 消息
     */
    private Long messages;

    /**
     * 消息详情
     */
    @JsonAlias("messages_details")
    private MessageStatsDetail messagesDetails;

    /**
     * 不明
     */
    @JsonAlias("messages_paged_out")
    private Long messagesPagedOut;

    /**
     * 不明
     */
    @JsonAlias("messages_persistent")
    private Long messagesPersistent;

    /**
     * 不明
     */
    @JsonAlias("messages_ram")
    private Long messagesRam;

    /**
     * 已入队
     */
    @JsonAlias("messages_ready")
    private Long messagesReady;

    /**
     * 已入队详情
     */
    @JsonAlias("messages_ready_details")
    private MessageStatsDetail messagesReadyDetails;

    /**
     * 不明
     */
    @JsonAlias("messages_ready_ram")
    private Long messagesReadyRam;

    /**
     * 未消费
     */
    @JsonAlias("messages_unacknowledged")
    private Long messagesUnacknowledged;

    /**
     * 未消费详情
     */
    @JsonAlias("messages_unacknowledged_details")
    private MessageStatsDetail messagesUnacknowledgedDetails;

    /**
     * 不明
     */
    @JsonAlias("messages_unacknowledged_ram")
    private Long messagesUnacknowledgedRam;

    /**
     * 队列名称
     */
    private String name;

    /**
     * 节点
     */
    private String node;

    /**
     * 不明
     */
    @JsonAlias("operator_policy")
    private String operatorPolicy;

    /**
     * 不明
     */
    private String policy;

    /**
     * 不明
     */
    @JsonAlias("recoverable_slaves")
    private String recoverableSlaves;

    /**
     * 不明
     */
    private Long reductions;

    /**
     * 不明
     */
    @JsonAlias("reductions_details")
    private MessageStatsDetail reductionsDetails;

    /**
     * 不明
     */
    @JsonAlias("single_active_consumer_tag")
    private String singleActiveConsumerTag;

    /**
     * 状态
     */
    private String state;

    /**
     * 类型
     */
    private String type;

    /**
     * 命名空间
     */
    private String vhost;

    /**
     * BackingQueueStatus
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class BackingQueueStatus {

        /**
         * 平均消费-出
         */
        @JsonAlias("avg_ack_egress_rate")
        private BigDecimal avgAckEgressRate;

        /**
         * 平均消费-进
         */
        @JsonAlias("avg_ack_ingress_rate")
        private BigDecimal avgAckIngressRate;

        /**
         * 平均-出
         */
        @JsonAlias("avg_egress_rate")
        private BigDecimal avgEgressRate;

        /**
         * 平均-进
         */
        @JsonAlias("avg_ingress_rate")
        private BigDecimal avgIngressRate;

        /**
         * 不明
         */
        private List<String> delta;

        /**
         * 长度
         */
        private Long len;

        /**
         * 模式
         */
        private String mode;

        /**
         * 下一个序列号
         */
        @JsonAlias("next_seq_id")
        private Long nextSeqId;

        /**
         * 不明
         */
        private Long q1;

        /**
         * 不明
         */
        private Long q2;

        /**
         * 不明
         */
        private Long q3;

        /**
         * 不明
         */
        private Long q4;

        /**
         * 不明
         */
        @JsonAlias("target_ram_count")
        private String targetRamCount;
    }

    /**
     * GarbageCollection
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class GarbageCollection {

        /**
         * 不明
         */
        @JsonAlias("fullsweep_after")
        private Long fullsweepAfter;

        /**
         * 不明
         */
        @JsonAlias("max_heap_size")
        private Long maxHeapSize;

        /**
         * 不明
         */
        @JsonAlias("min_bin_vheap_size")
        private Long minBinVheapSize;

        /**
         * 不明
         */
        @JsonAlias("min_heap_size")
        private Long minHeapSize;

        /**
         * 不明
         */
        @JsonAlias("minor_gcs")
        private Long minorGcs;

    }

    /**
     * MessageStats
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class MessageStats {

        /**
         * 消费
         */
        private Long ack;

        /**
         * 消费详情
         */
        @JsonAlias("ack_details")
        private MessageStatsDetail ackDetails;

        /**
         * 不明
         */
        private Long deliver;

        /**
         * 不明
         */
        @JsonAlias("deliver_details")
        private MessageStatsDetail deliverDetails;

        /**
         * 不明
         */
        @JsonAlias("deliver_get")
        private Long deliverGet;

        /**
         * 不明
         */
        @JsonAlias("deliver_get_details")
        private MessageStatsDetail deliverGetDetails;

        /**
         * 未消费
         */
        @JsonAlias("deliver_no_ack")
        private Long deliverNoAck;

        /**
         * 未消费详情
         */
        @JsonAlias("deliver_no_ack_details")
        private MessageStatsDetail deliverNoAckDetails;

        /**
         * 不明
         */
        private Long get;

        /**
         * 不明
         */
        @JsonAlias("get_details")
        private MessageStatsDetail getDetails;

        /**
         * 不明
         */
        @JsonAlias("get_empty")
        private Long getEmpty;

        /**
         * 不明
         */
        @JsonAlias("get_empty_details")
        private MessageStatsDetail getEmptyDetails;

        /**
         * 不明
         */
        @JsonAlias("get_no_ack")
        private Long getNoAck;

        /**
         * 不明
         */
        @JsonAlias("get_no_ack_details")
        private MessageStatsDetail getNoAckDetails;

        /**
         * 推送
         */
        private Long publish;

        /**
         * 推送详情
         */
        @JsonAlias("publish_details")
        private MessageStatsDetail publishDetails;

        /**
         * 不明
         */
        private Long redeliver;

        /**
         * 不明
         */
        @JsonAlias("redeliver_details")
        private MessageStatsDetail redeliverDetails;
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

    /**
     * Arguments
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class Arguments {

        /**
         * 死信交换机
         */
        @JsonAlias("x-dead-letter-exchange")
        private String xDeadLetterExchange;

        /**
         * 死信路由key
         */
        @JsonAlias("x-dead-letter-routing-key")
        private String xDeadLetterRoutingKey;

        /**
         * 最大存活时间
         */
        @JsonAlias("x-message-ttl")
        private String xMessageTtl;
    }
}
