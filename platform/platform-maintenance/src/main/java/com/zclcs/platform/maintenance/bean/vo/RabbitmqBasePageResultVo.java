package com.zclcs.platform.maintenance.bean.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * RabbitmqBasePageResultVo
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RabbitmqBasePageResultVo<T> {

    /**
     * 过滤后的总记录数
     */
    @JsonAlias("filtered_count")
    private Integer filteredCount;

    /**
     * 数据数量
     */
    @JsonAlias("item_count")
    private Integer itemCount;

    /**
     * 数据
     */
    private List<T> items;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 总页码
     */
    @JsonAlias("page_count")
    private Integer pageCount;

    /**
     * 每页数据条数
     */
    @JsonAlias("page_size")
    private Integer pageSize;

    /**
     * 总记录数
     */
    @JsonAlias("total_count")
    private Integer totalCount;

}
