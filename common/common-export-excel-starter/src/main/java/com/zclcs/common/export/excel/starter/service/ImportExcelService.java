package com.zclcs.common.export.excel.starter.service;

import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
public interface ImportExcelService<T, R> {

    R toExcelVo(Map<String, String> cellData);
    
    T toBean(R excelVo);

    void saveBeans(List<T> t);

}
