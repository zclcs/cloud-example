package com.zclcs.cloud.lib.core.utils;

import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.exception.MyException;
import lombok.experimental.UtilityClass;

/**
 * @author zclcs
 */
@UtilityClass
public class RspUtil {

    /**
     * 成功不带参
     *
     * @return 返回json
     */
    public BaseRsp<Object> message() {
        return new BaseRsp<>().setMsg(CommonCore.SUCCESS_MSG);
    }

    /**
     * 调用成功 - 消息体
     *
     * @param data 数据体
     * @return 返回json
     */
    public BaseRsp<Object> message(Object data) {
        BaseRsp<Object> vo = new BaseRsp<>();
        vo.setData(data);
        return vo;
    }

    /**
     * 调用成功 - 消息体
     *
     * @param data 数据体
     * @return 返回json
     */
    public BaseRsp<Object> message(String msg, Object data) {
        BaseRsp<Object> vo = new BaseRsp<>();
        vo.setMsg(msg);
        vo.setData(data);
        return vo;
    }

    /**
     * 成功消息
     *
     * @param message 消息体
     * @return 返回json
     */
    public <T> BaseRsp<T> message(String message) {
        BaseRsp<T> vo = new BaseRsp<>();
        vo.setMsg(message);
        return vo;
    }

    /**
     * 调用失败 - 异常
     *
     * @param myException 异常
     * @return 返回json
     */
    public <T> BaseRsp<T> message(MyException myException) {
        BaseRsp<T> vo = new BaseRsp<>();
        vo.setMsg(myException.getMessage());
        return vo;
    }

    /**
     * 调用成功 - 返回数据
     *
     * @param data 数据
     * @return 返回json
     */
    public <T> BaseRsp<T> data(T data) {
        BaseRsp<T> vo = new BaseRsp<>();
        vo.setData(data);
        return vo;
    }
}
