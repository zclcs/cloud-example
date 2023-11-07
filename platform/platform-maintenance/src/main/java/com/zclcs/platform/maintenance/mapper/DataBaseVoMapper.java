//package com.zclcs.platform.maintenance.mapper;
//
//import com.zclcs.platform.maintenance.bean.vo.DataBaseDataVo;
//import com.zclcs.platform.maintenance.bean.vo.VueColumnVo;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Component;
//
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author zhouc
// */
//@Component
//public class DataBaseVoMapper implements RowMapper<DataBaseDataVo> {
//    @Override
//    public DataBaseDataVo mapRow(ResultSet rs, int rowNum) throws SQLException {
//        ResultSetMetaData metaData = rs.getMetaData();
//        int columnCount = metaData.getColumnCount();
//        List<VueColumnVo> columnVos = new ArrayList<>();
//        List<Map<String, Object>> dataList = new ArrayList<>();
//        for (int i = 0; i < columnCount; i++) {
//            //通过序号获取列名,起始值为1
//            VueColumnVo vueColumnVo = new VueColumnVo();
//            String columnName = metaData.getColumnName(i + 1);
//            vueColumnVo.setTitle(columnName);
//            vueColumnVo.setDataIndex(columnName);
//            columnVos.add(vueColumnVo);
//        }
//        while (rs.next()) {
//            Map<String, Object> data = new HashMap<>();
//            for (int i = 0; i < columnCount; i++) {
//                String columnName = metaData.getColumnName(i + 1);
//                //通过列名获取值.如果列值为空,columnValue为null,不是字符型
//                Object object = rs.getObject(columnName);
//                data.put(columnName, object);
//            }
//            dataList.add(data);
//        }
//        return DataBaseDataVo.builder()
//                .columns(columnVos)
//                .data(dataList)
//                .build();
//    }
//}
