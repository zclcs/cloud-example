package com.zclcs.common.export.excel.starter.service;

import java.util.List;

/**
 * @author zclcs
 */
public interface ExportExcelService<T, K> {

    /**
     * 统计导出条数
     *
     * @param t 入参泛型
     * @return 总条数
     */
    Long count(T t);

    /**
     * 手动分页查询
     *
     * @param t          入参泛型
     * @param startIndex 开始
     * @param endIndex   结束
     * @return 出参泛型
     */
    List<K> getDataPaginateAs(T t, Long pageNum, Long pageSize, Long totalRows);

}
