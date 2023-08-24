package com.zclcs.common.export.excel.starter.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zclcs
 */
@UtilityClass
public class ExcelUtil {


    /**
     * 默认：{@link ExcelTypeEnum#XLSX}
     * sheetName = fileName
     *
     * @see ExcelUtil#export(HttpServletResponse, String, Class, String, List, ExcelTypeEnum)
     */
    public <T> void export(HttpServletResponse response, String fileName, Class<T> clazz, List<T> data) throws IOException {
        export(response, fileName, clazz, fileName, data, ExcelTypeEnum.XLSX);
    }

    /**
     * 默认：{@link ExcelTypeEnum#XLSX}
     *
     * @see ExcelUtil#export(HttpServletResponse, String, Class, String, List, ExcelTypeEnum)
     */
    public <T> void export(HttpServletResponse response, String fileName, Class<T> clazz, String sheetName, List<T> data) throws IOException {
        export(response, fileName, clazz, sheetName, data, ExcelTypeEnum.XLSX);
    }

    /**
     * 导出（数据量小可以用）<= 10000
     *
     * @param response      响应
     * @param fileName      文件名称
     * @param clazz         类路径
     * @param sheetName     sheet名称
     * @param data          数据
     * @param excelTypeEnum excel类型
     * @param <T>           泛型
     * @throws IOException io异常
     */
    public <T> void export(HttpServletResponse response, String fileName, Class<T> clazz, String sheetName, List<T> data, ExcelTypeEnum excelTypeEnum) throws IOException {
        setResponse(response, fileName, excelTypeEnum);
        ServletOutputStream outputStream = response.getOutputStream();
        excelWriterBuilder(outputStream, clazz).sheet(sheetName).doWrite(data);
        outputStream.close();
    }

    /**
     * 导出（数据量大可以用）
     * 手动处理
     *
     * @param response      响应
     * @param fileName      文件名称
     * @param clazz         类路径
     * @param excelTypeEnum excel类型
     * @param <T>           泛型
     * @throws IOException io异常
     */
    public <T> ExcelWriter excelWriter(HttpServletResponse response, String fileName, Class<T> clazz, ExcelTypeEnum excelTypeEnum) throws IOException {
        setResponse(response, fileName, excelTypeEnum);
        ServletOutputStream outputStream = response.getOutputStream();
        return excelWriterBuilder(outputStream, clazz).build();
    }

    public <T> ExcelWriterBuilder excelWriterBuilder(ServletOutputStream outputStream, Class<T> clazz) throws IOException {
        return EasyExcel.write(outputStream, clazz);
    }


    public void setResponse(HttpServletResponse response, String fileName, ExcelTypeEnum excelTypeEnum) {
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
