package com.zclcs.platform.maintenance.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "RedisVo对象", description = "redis")
public class RedisVo {

    /**
     * 服务名
     */
    @Schema(description = "redis key")
    private String key;

    /**
     * 服务名
     */
    @Schema(description = "值")
    private Object value;

    /**
     * 服务名
     */
    @Schema(description = "过期时间(秒)")
    private Long ttl;
}
