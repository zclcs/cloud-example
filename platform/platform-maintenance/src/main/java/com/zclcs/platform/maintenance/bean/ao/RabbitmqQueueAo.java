package com.zclcs.platform.maintenance.bean.ao;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 队列查询 Ao
 *
 * @author zclcs
 * @since 2023-01-10 10:39:10.151
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RabbitmqQueueAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 队列名称
     */
    private String name;


}