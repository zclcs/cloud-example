package com.zclcs.platform.system.service;

import com.zclcs.platform.system.api.bean.vo.DataBaseDataVo;

import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
public interface DataBaseService {

    Map<String, List<String>> getSchema(String dataBaseType);

    DataBaseDataVo getData(String dataBaseType, String sql);
}
