package com.zclcs.platform.system.api.entity.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class RedisAo {

    @Schema(description = "redis key")
    @NotBlank(message = "{required}")
    private String key;

    @Schema(description = "值")
    @NotBlank(message = "{required}")
    private String value;

    @Schema(description = "过期时间(秒)")
    private Long ttl;
}
