package com.zclcs.common.dict.core.properties;

/**
 * 消息类型
 *
 * @author zclcs
 */
public enum MqType {
    /**
     * 不使用消息中间件通知其他系统刷新字典，或者自定义处理此类情况
     */
    NONE,
    /**
     * 使用 AMQP（RabbitMQ） 通知其他系统刷新字典
     */
    AMQP,
    ;
}
