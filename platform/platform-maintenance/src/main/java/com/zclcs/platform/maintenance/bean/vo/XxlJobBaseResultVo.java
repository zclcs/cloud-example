package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * XxlJobBaseResultVo
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
public class XxlJobBaseResultVo<T> {

    /**
     * code
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 内容
     */
    private T content;

    public boolean success() {
        return this.code == 200;
    }

    public boolean failure() {
        return this.code != 200;
    }
}
