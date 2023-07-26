package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * redis Vo
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
public class RedisVo {

    /**
     * redis key
     */
    private String key;

    /**
     * 值
     */
    private Object value;

    /**
     * 过期时间(秒)
     */
    private Long ttl;
}
