package com.zclcs.platform.maintenance.bean.ao;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 交换机查询 Ao
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
public class RabbitmqExchangeAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 交换机名称
     */
    private String name;


}