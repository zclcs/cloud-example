package com.zclcs.nacos.config.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * NacosTokenVo
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

    private Boolean globalAdmin;

    private String username;
}
