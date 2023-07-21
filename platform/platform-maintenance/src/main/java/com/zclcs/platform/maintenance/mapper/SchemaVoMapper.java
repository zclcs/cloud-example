package com.zclcs.platform.maintenance.mapper;

import com.zclcs.platform.maintenance.bean.vo.SchemaVo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhouc
 */
@Component
public class SchemaVoMapper implements RowMapper<SchemaVo> {
    @Override
    public SchemaVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return SchemaVo.builder()
                .tableSchema(rs.getString("table_schema"))
                .columnName(rs.getString("column_name"))
                .build();
    }
}
