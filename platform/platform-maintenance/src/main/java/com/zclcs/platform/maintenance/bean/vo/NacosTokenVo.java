package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Nacos Token Vo
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
public class NacosTokenVo {

    /**
     * nacos token
     */
    private String accessToken;

    /**
     * 过期时间(秒)
     */
    private Long tokenTtl;
}
