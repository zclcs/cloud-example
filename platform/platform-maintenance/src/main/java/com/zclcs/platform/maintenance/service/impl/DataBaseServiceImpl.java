package com.zclcs.platform.maintenance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.platform.maintenance.bean.vo.DataBaseDataVo;
import com.zclcs.platform.maintenance.bean.vo.SchemaVo;
import com.zclcs.platform.maintenance.bean.vo.VueColumnVo;
import com.zclcs.platform.maintenance.mapper.SchemaVoMapper;
import com.zclcs.platform.maintenance.service.DataBaseService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author zclcs
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseServiceImpl implements DataBaseService {

    private static final Pattern PATTERN = Pattern.compile("[^0-9]");

    private final SchemaVoMapper schemaVoMapper;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setXxlJobDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, List<String>> getSchema(String dataBaseType) {
        List<SchemaVo> schema = jdbcTemplate.query("SELECT concat(t.TABLE_SCHEMA, '.', t.TABLE_NAME) table_schema, c.COLUMN_NAME column_name" +
                "        FROM information_schema.TABLES t" +
                "                 inner join information_schema.COLUMNS c" +
                "                            on t.TABLE_SCHEMA = c.TABLE_SCHEMA and t.TABLE_NAME = c.TABLE_NAME" +
                "        where t.TABLE_SCHEMA not in ('information_schema', 'mysql', 'sys', 'performance_schema')", schemaVoMapper);
        List<List<SchemaVo>> tableSchema = CollectionUtil.groupByField(schema, "tableSchema");
        LinkedHashMap<String, List<String>> stringListLinkedHashMap = new LinkedHashMap<>();
        for (List<SchemaVo> schemaVos : tableSchema) {
            List<String> columnNames = schemaVos.stream().map(SchemaVo::getColumnName).collect(Collectors.toList());
            stringListLinkedHashMap.put(schemaVos.get(0).getTableSchema(), columnNames);
        }
        return stringListLinkedHashMap;
    }

    @SneakyThrows
    @Override
    public DataBaseDataVo getData(String dataBaseType, String sql) {
        if (!sql.matches("^(?i)(\\s*)(select)(\\s+)(((?!(insert|delete|update)).)+)$")) {
            throw new MyException("目前只支持查询");
        }
        if (!StrUtil.containsIgnoreCase(sql, CommonCore.COUNT)) {
            if (!StrUtil.containsIgnoreCase(sql, CommonCore.LIMIT)) {
                sql += " limit 99";
            } else {
                int lastIndexOfIgnoreCase = StrUtil.lastIndexOfIgnoreCase(sql, CommonCore.LIMIT);
                String sub = StrUtil.sub(sql, lastIndexOfIgnoreCase, sql.length());
                Matcher m = PATTERN.matcher(sub);
                String limitNumber = m.replaceAll("").trim();
                BigDecimal bigDecimal = new BigDecimal(limitNumber);
                if (bigDecimal.intValue() > 100) {
                    throw new MyException("仅仅用作开发排查问题，不要查询过多的数据");
                }
            }
        }
        DataBaseDataVo dataBaseDataVo = new DataBaseDataVo();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            //获取列集
            ResultSetMetaData metaData = rs.getMetaData();
            //获取列的数量
            int columnCount = metaData.getColumnCount();
            List<VueColumnVo> columnVos = new ArrayList<>();
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                //通过序号获取列名,起始值为1
                VueColumnVo vueColumnVo = new VueColumnVo();
                String columnName = metaData.getColumnName(i + 1);
                vueColumnVo.setTitle(columnName);
                vueColumnVo.setDataIndex(columnName);
                columnVos.add(vueColumnVo);
            }
            dataBaseDataVo.setColumns(columnVos);
            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    //通过列名获取值.如果列值为空,columnValue为null,不是字符型
                    Object object = rs.getObject(columnName);
                    data.put(columnName, object);
                }
                dataList.add(data);
            }
            dataBaseDataVo.setData(dataList);
        }
        return dataBaseDataVo;
    }
}
