package com.zclcs.common.export.excel.starter.service;

import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
public interface ImportExcelService<T> {

    T toBean(Map<String, String> cellData);

    void saveBeans(List<T> t);

}
