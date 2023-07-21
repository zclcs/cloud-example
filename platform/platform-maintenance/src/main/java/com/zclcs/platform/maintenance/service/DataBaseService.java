package com.zclcs.platform.maintenance.service;

import com.zclcs.platform.maintenance.bean.vo.DataBaseDataVo;

import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
public interface DataBaseService {

    Map<String, List<String>> getSchema(String dataBaseType);

    DataBaseDataVo getData(String dataBaseType, String sql);
}
