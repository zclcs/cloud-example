package com.zclcs.platform.maintenance.bean.ao;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询nacos配置 Ao
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
public class NacosConfigAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 命名空间
     */
    private String dataId;


}