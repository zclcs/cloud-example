package com.zclcs.platform.system.service;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.Column;
import com.zclcs.platform.system.api.entity.Table;
import com.zclcs.platform.system.api.entity.ao.GenerateAo;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * @author zclcs
 */
public interface GeneratorService {

    /**
     * 获取数据库列表
     *
     * @param databaseType databaseType
     * @return 数据库列表
     */
    List<String> getDatabases(String databaseType);

    /**
     * 获取数据表
     *
     * @param tableName    tableName
     * @param page         request
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @return 数据表分页数据
     */
    BasePage<Table> getTables(String tableName, BasePageAo page, String databaseType, String schemaName);

    /**
     * 获取数据表列信息
     *
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @param tableName    tableName
     * @param excludeColumns 需要排除的字段
     * @return 数据表列信息
     */
    List<Column> getColumns(String databaseType, String schemaName, String tableName, List<String> excludeColumns);

    /**
     * 代码生成
     * @param generateAo 参数
     * @param response 返回
     */
    void generate(GenerateAo generateAo, HttpServletResponse response);
}
