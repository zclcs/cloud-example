package com.zclcs.platform.maintenance.bean.ao;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * redis Vo
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeleteRedisAo {

    /**
     * redis key
     */
    @NotBlank(message = "{required}")
    private String keys;
}
