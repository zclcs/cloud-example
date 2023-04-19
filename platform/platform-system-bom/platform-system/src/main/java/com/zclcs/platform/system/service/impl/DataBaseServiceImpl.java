package com.zclcs.platform.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.common.core.constant.MyConstant;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;
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
        if (!StrUtil.containsIgnoreCase(sql, MyConstant.SELECT)) {
            throw new MyException("目前只支持查询");
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
                    if (object == null) {
                        object = "";
                    }
                    data.put(columnName, object);
                }
                dataList.add(data);
            }
            dataBaseDataVo.setData(dataList);
        }
        return dataBaseDataVo;
    }
}
