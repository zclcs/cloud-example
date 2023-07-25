package com.zclcs.platform.maintenance.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

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
@Schema(title = "XxlJobBasePageResultVo", description = "XxlJobBasePageResultVo")
public class XxlJobBasePageResultVo<T> {

    /**
     * 服务名
     */
    @Schema(description = "总记录数")
    private Integer recordsTotal;

    /**
     * 服务名
     */
    @Schema(description = "过滤后的总记录数")
    private Integer recordsFiltered;

    /**
     * 服务名
     */
    @Schema(description = "内容")
    private List<T> data;

}
