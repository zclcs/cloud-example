package com.zclcs.cloud.lib.rabbit.mq.entity;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 测试消息体
 * </p>
 *
 * @author zclcs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageStruct implements Serializable {

    @Serial
    private static final long serialVersionUID = 392365881428311040L;

    private String message;

}
