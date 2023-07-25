package com.zclcs.platform.maintenance.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
     * 服务名
     */
    @Schema(description = "nacos token")
    private String accessToken;

    /**
     * 服务名
     */
    @Schema(description = "过期时间(秒)")
    private Long tokenTtl;
}
