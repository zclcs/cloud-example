package com.zclcs.platform.maintenance.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文本分页
 *
 * @author tjq
 * @since 2020/5/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PowerJobStringPageVo {
    /**
     * 当前页数
     */
    private long index;
    /**
     * 总页数
     */
    private long totalPages;
    /**
     * 文本数据
     */
    private String data;
}
