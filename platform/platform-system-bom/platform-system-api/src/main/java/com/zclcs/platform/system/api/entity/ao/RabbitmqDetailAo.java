package com.zclcs.platform.system.api.entity.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "RabbitmqExchangeAo", description = "RabbitmqExchangeAo")
public class RabbitmqDetailAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "命名空间")
    private String vhost;

    @Schema(description = "交换机名称")
    private String name;


}