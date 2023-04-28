package com.zclcs.platform.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.common.core.constant.CommonCore;
import com.zclcs.common.core.exception.MyException;
import com.zclcs.platform.system.api.entity.vo.DataBaseDataVo;
import com.zclcs.platform.system.api.entity.vo.SchemaVo;
import com.zclcs.platform.system.api.entity.vo.VueColumnVo;
import com.zclcs.platform.system.mapper.DataBaseMapper;
import com.zclcs.platform.system.service.DataBaseService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
 * @author zhouc
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DataBaseServiceImpl implements DataBaseService {

    private final DataBaseMapper dataBaseMapper;

    private final DataSource dataSource;

    @Override
    public Map<String, List<String>> getSchema(String dataBaseType) {
        List<SchemaVo> schema = dataBaseMapper.getSchema(dataBaseType);
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
        if (!StrUtil.containsIgnoreCase(sql, CommonCore.SELECT)) {
            throw new MyException("目前只支持查询");
        }
        if (!StrUtil.containsIgnoreCase(sql, CommonCore.COUNT)) {
            if (!StrUtil.containsIgnoreCase(sql, CommonCore.LIMIT)) {
                sql += " limit 999";
            } else {
                int lastIndexOfIgnoreCase = StrUtil.lastIndexOfIgnoreCase(sql, CommonCore.LIMIT);
                String sub = StrUtil.sub(sql, lastIndexOfIgnoreCase, sql.length());
                Pattern p = Pattern.compile("[^0-9]");
                Matcher m = p.matcher(sub);
                String limitNumber = m.replaceAll("").trim();
                BigDecimal bigDecimal = new BigDecimal(limitNumber);
                if (bigDecimal.intValue() > 1000) {
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
