package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * XxlJobBasePageResultVo
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
public class XxlJobBasePageResultVo<T> {

    /**
     * 总记录数
     */
    private Integer recordsTotal;

    /**
     * 过滤后的总记录数
     */
    private Integer recordsFiltered;

    /**
     * 内容
     */
    private List<T> data;

}
