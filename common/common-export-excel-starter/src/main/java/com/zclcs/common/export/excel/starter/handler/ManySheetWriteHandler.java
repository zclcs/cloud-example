package com.zclcs.common.export.excel.starter.handler;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zclcs.common.export.excel.starter.annotation.ResponseExcel;
import com.zclcs.common.export.excel.starter.annotation.Sheet;
import com.zclcs.common.export.excel.starter.enhance.WriterBuilderEnhancer;
import com.zclcs.common.export.excel.starter.kit.ExcelException;
import com.zclcs.common.export.excel.starter.properites.ExcelConfigProperties;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zclcs
 * @since 2020/3/29
 */
public class ManySheetWriteHandler extends AbstractSheetWriteHandler {

    public ManySheetWriteHandler(ExcelConfigProperties configProperties,
                                 ObjectProvider<List<Converter<?>>> converterProvider, WriterBuilderEnhancer excelWriterBuilderEnhance) {
        super(configProperties, converterProvider, excelWriterBuilderEnhance);
    }

    /**
     * 当且仅当List不为空且List中的元素也是List 才返回true
     *
     * @param obj 返回对象
     * @return boolean
     */
    @Override
    public boolean support(Object obj) {
        if (obj instanceof List<?> objList) {
            return !objList.isEmpty() && objList.get(0) instanceof List;
        } else {
            throw new ExcelException("@ResponseExcel 返回值必须为List类型");
        }
    }

    @Override
    public void write(Object obj, HttpServletResponse response, ResponseExcel responseExcel) {
        List<?> objList = (List<?>) obj;
        ExcelWriter excelWriter = getExcelWriter(response, responseExcel);

        Sheet[] sheets = responseExcel.sheets();
        WriteSheet sheet;
        for (int i = 0; i < sheets.length; i++) {
            List<?> eleList = (List<?>) objList.get(i);

            if (CollectionUtils.isEmpty(eleList)) {
                sheet = EasyExcel.writerSheet(responseExcel.sheets()[i].sheetName()).build();
            } else {
                // 有模板则不指定sheet名
                Class<?> dataClass = eleList.get(0).getClass();
                sheet = this.sheet(responseExcel.sheets()[i], dataClass, responseExcel.template(),
                        responseExcel.headGenerator());
            }

            // 填充 sheet
            if (responseExcel.fill()) {
                excelWriter.fill(eleList, sheet);
            } else {
                // 写入sheet
                excelWriter.write(eleList, sheet);
            }
        }
        excelWriter.finish();
    }

}
