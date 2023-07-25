package com.zclcs.cloud.lib.core.base;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息响应格式
 *
 * @author zclcs
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseRsp<T> {

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应体
     */
    private T data;

    /**
     * 时间戳
     */
    private long time;

    public BaseRsp() {
        this.time = System.currentTimeMillis();
    }

    public BaseRsp(String msg) {
        this.msg = msg;
        this.time = System.currentTimeMillis();
    }

    public BaseRsp(String msg, T data) {
        this.msg = msg;
        this.data = data;
        this.time = System.currentTimeMillis();
    }
}
