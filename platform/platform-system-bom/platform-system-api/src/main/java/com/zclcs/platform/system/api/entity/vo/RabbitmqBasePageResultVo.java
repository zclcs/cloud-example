package com.zclcs.platform.system.api.entity.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
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
@Schema(title = "RabbitmqBasePageResultVo", description = "RabbitmqBasePageResultVo")
public class RabbitmqBasePageResultVo<T> {

    @Schema(description = "过滤后的总记录数")
    @JsonAlias("filtered_count")
    private Integer filteredCount;

    @Schema(description = "数据数量")
    @JsonAlias("item_count")
    private Integer itemCount;

    @Schema(description = "数据")
    private List<T> items;

    @Schema(description = "当前页")
    private Integer page;

    @Schema(description = "总页码")
    @JsonAlias("page_count")
    private Integer pageCount;

    @Schema(description = "每页数据条数")
    @JsonAlias("page_size")
    private Integer pageSize;

    @Schema(description = "总记录数")
    @JsonAlias("total_count")
    private Integer totalCount;

}
