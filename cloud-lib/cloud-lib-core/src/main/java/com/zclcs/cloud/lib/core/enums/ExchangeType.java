package com.zclcs.cloud.lib.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zclcs
 */
@Getter
@AllArgsConstructor
public enum ExchangeType {

    /**
     * 直通交换机，又被叫做直连交换机，即 Direct Exchange ，、
     * 是可以直接将消息根据特定匹配规则发送到对应的消息队列的交换机，
     * 如果匹配规则相同，则一条消息可以被发送到多个对应的消息队列上，
     * 而这个匹配规则是通过 routing_key 来进行匹配。
     */
    DIRECT(".direct"),

    /**
     * 扇形交换机，即 Fanout Exchange ，是通过类似广播的形式，将消息传递到消息队列中去，
     * 与直通交换机不同的是，扇形交换机不需要绑定 routing_key ，
     * 会将消息传递到所有与该交换机绑定的消息队列中去。
     */
    FANOUT(".fanout"),


    /**
     * 主题交换机，即 Topic Exchange ，是通过 routing_key 与
     * bidding_key 的匹配规则进行消息传递的一种交换机。
     * 与直通交换机不同的是，直通交换机中的 routing_key 和
     * bidding_key 的名称必须保持一致，但是在主题交换机中，
     * bidding_key 会通过一定的规则去匹配 routing_key ，
     * 以此将消息发送到相匹配的消息队列中去。
     * Tips: 交换机与队列之间进行绑定的 key ，被称为 bidding_key ，消息与交换机之间进行绑定的 key ，被称为 routing_key 。
     */
    TOPIC(".topic"),


    /**
     * 延迟队列 不是rabbitmq官方支持的队列 第三方开发
     * 场景：待支付订单超时自动取消等
     * Tips: 迟队列最大延迟时间不能超过4294967296毫秒大约49.6天
     */
    DELAYED(".delayed"),
    ;

    private final String subPrefix;
}
