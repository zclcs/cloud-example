package com.zclcs.nacos.config.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * nacos 返回结构体
 *
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NacosBaseDataVo<T> {

    /**
     * code
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    public T data;

    public boolean success() {
        return this.code == 200;
    }
}
