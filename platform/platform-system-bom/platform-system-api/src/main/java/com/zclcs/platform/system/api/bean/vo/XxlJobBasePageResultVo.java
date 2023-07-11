package com.zclcs.platform.system.api.bean.vo;

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

    @Schema(description = "总记录数")
    private Integer recordsTotal;

    @Schema(description = "过滤后的总记录数")
    private Integer recordsFiltered;

    @Schema(description = "内容")
    private List<T> data;

}