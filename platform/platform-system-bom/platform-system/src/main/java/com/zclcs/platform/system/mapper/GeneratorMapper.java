package com.zclcs.platform.system.mapper;

import com.zclcs.platform.system.api.bean.entity.ColumnInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zclcs
 */
public interface GeneratorMapper {

    /**
     * 获取数据列表
     *
     * @param databaseType databaseType
     * @return 数据库列表
     */
    List<String> getDatabases(@Param("databaseType") String databaseType);

    /**
     * 获取数据表列信息
     *
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @param tableName    tableName
     * @return 数据表列信息
     */
    List<ColumnInfo> getColumns(@Param("databaseType") String databaseType, @Param("schemaName") String schemaName, @Param("tableName") String tableName, @Param("excludeColumns") List<String> excludeColumns);
}
