package com.zclcs.common.export.excel.starter.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zclcs.common.export.excel.starter.kit.ExcelException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zclcs
 */
public interface ExcelService<T, K> {

    Long SINGLE_LIMIT = 10000L;

    Long BATCH_SIZE = 10000L;

    Long BATCH_LIMIT = 100000L;

    Long count(T t);

    List<K> getDataWithIndex(T t, Long startIndex, Long endIndex);

    default void export(HttpServletResponse response, String fileName, Class<K> clazz, T t) throws Exception {
        export(response, fileName, clazz, fileName, ExcelTypeEnum.XLSX, t);
    }

    default void export(HttpServletResponse response, String fileName, Class<K> clazz, String sheetName, ExcelTypeEnum excelTypeEnum, T t) throws Exception {
        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream, clazz).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        Long count = count(t);
        if (count <= SINGLE_LIMIT) {
            List<K> dataWithIndex = getDataWithIndex(t, 0L, SINGLE_LIMIT);
            excelWriter.write(dataWithIndex, writeSheet);
        } else if (count > BATCH_LIMIT) {
            throw new ExcelException("导出数量过大");
        } else {
            BigDecimal bd = new BigDecimal(count);
            long pageSize = bd.divide(new BigDecimal(BATCH_SIZE), RoundingMode.UP).longValue();
            for (int i = 1; i <= pageSize; i++) {
                List<K> dataWithIndex = getDataWithIndex(t, (i - 1) * BATCH_SIZE, BATCH_SIZE);
                excelWriter.write(dataWithIndex, writeSheet);
                dataWithIndex = null;
            }
        }
        setResponse(response, fileName, excelTypeEnum);
        excelWriter.finish();
    }

    default void setResponse(HttpServletResponse response, String fileName, ExcelTypeEnum excelTypeEnum) {
        fileName = String.format("%s%s", URLEncoder.encode(fileName, StandardCharsets.UTF_8), excelTypeEnum.getValue());
        String contentType = MediaTypeFactory.getMediaType(fileName)
                .map(MediaType::toString)
                .orElse("application/vnd.ms-excel");
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + fileName);
    }

}
