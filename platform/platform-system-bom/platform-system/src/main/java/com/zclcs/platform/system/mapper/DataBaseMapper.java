package com.zclcs.platform.system.mapper;

import com.zclcs.platform.system.api.entity.vo.SchemaVo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zclcs
 */
public interface DataBaseMapper {

    /**
     * 获取 Schema
     *
     * @param databaseType 数据库类型
     * @return {@link SchemaVo}
     */
    List<SchemaVo> getSchema(@Param("databaseType") String databaseType);

    /**
     * 获取数据
     *
     * @param databaseType 数据库类型
     * @param sql          语句
     * @return 数据
     */
    List<LinkedHashMap<String, Object>> getData(@Param("databaseType") String databaseType, @Param("sql") String sql);
}
