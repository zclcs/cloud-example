package com.zclcs.common.export.excel.starter.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.zclcs.common.export.excel.starter.kit.ExcelException;
import com.zclcs.common.export.excel.starter.service.ExportExcelService;
import com.zclcs.common.export.excel.starter.utils.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zclcs
 */
public class SimpleExportListener<T, K> {

    /**
     * 单次查询导出大小限制，超过限制使用分页导出
     */
    private Long singleLimit = 5000L;

    /**
     * 分页导出一次性取多少数据
     */
    private Long batchSize = 5000L;

    /**
     * 总体限制，超过该大小的数据不允许导出
     */
    private Long batchLimit = 100000L;

    private ExportExcelService<T, K> exportExcelService;

    public SimpleExportListener(ExportExcelService<T, K> exportExcelService) {
        this.exportExcelService = exportExcelService;
    }

    public SimpleExportListener(ExportExcelService<T, K> exportExcelService, Long singleLimit, Long batchSize, Long batchLimit) {
        this.exportExcelService = exportExcelService;
        this.singleLimit = singleLimit;
        this.batchSize = batchSize;
        this.batchLimit = batchLimit;
    }

    /**
     * 使用注解导出
     * sheetName = fileName
     * 文件后缀 {@link ExcelTypeEnum#XLSX}
     *
     * @param response 返回
     * @param fileName 文件名称
     * @param clazz    类路径
     * @param t        入参泛型
     */
    public void exportWithEntity(HttpServletResponse response, String fileName, Class<K> clazz, T t) {
        exportWithEntity(response, fileName, fileName, clazz, t, ExcelTypeEnum.XLSX);
    }

    /**
     * 使用注解导出
     * 使用默认的表格样式策略
     *
     * @param response      返回
     * @param fileName      文件名称
     * @param sheetName     sheet名称
     * @param clazz         类路径
     * @param t             入参泛型
     * @param excelTypeEnum {@link ExcelTypeEnum}
     */
    public void exportWithEntity(HttpServletResponse response, String fileName, String sheetName,
                                 Class<K> clazz, T t,
                                 ExcelTypeEnum excelTypeEnum) {
        exportWithEntity(response, fileName, sheetName, clazz, t, defaultWriteHandler(), excelTypeEnum);
    }

    /**
     * 使用注解导出
     *
     * @param response      返回
     * @param fileName      文件名称
     * @param sheetName     sheet名称
     * @param clazz         类路径
     * @param t             入参泛型
     * @param writeHandler  自定义表头处理
     * @param excelTypeEnum {@link ExcelTypeEnum}
     */
    @SneakyThrows
    public void exportWithEntity(HttpServletResponse response, String fileName, String sheetName,
                                 Class<K> clazz, T t,
                                 WriteHandler writeHandler,
                                 ExcelTypeEnum excelTypeEnum) {
        Long count = checkSize(t);
        ExcelWriter excelWriter = EasyExcel
                .write(response.getOutputStream(), clazz)
                .registerWriteHandler(writeHandler)
                .build();
        responseExcel(sheetName, t, count, excelWriter);
        ExcelUtil.setResponse(response, fileName, excelTypeEnum);
        excelWriter.finish();
    }

    /**
     * 动态表头导出
     * 使用默认的表格样式策略
     *
     * @param response      返回
     * @param fileName      文件名称
     * @param sheetName     sheet名称
     * @param head          动态表头
     * @param t             入参泛型
     * @param excelTypeEnum {@link ExcelTypeEnum}
     */
    public void exportWithDynamicHead(HttpServletResponse response,
                                      String fileName, String sheetName,
                                      List<List<String>> head, T t,
                                      ExcelTypeEnum excelTypeEnum) {
        exportWithDynamicHead(response, fileName, sheetName, head, t, defaultWriteHandler(), excelTypeEnum);
    }


    /**
     * 动态表头导出
     *
     * @param response      返回
     * @param fileName      文件名称
     * @param sheetName     sheet名称
     * @param head          动态表头
     * @param t             入参泛型
     * @param writeHandler  表格样式处理
     * @param excelTypeEnum {@link ExcelTypeEnum}
     */
    @SneakyThrows
    public void exportWithDynamicHead(HttpServletResponse response,
                                      String fileName, String sheetName,
                                      List<List<String>> head, T t,
                                      WriteHandler writeHandler,
                                      ExcelTypeEnum excelTypeEnum) {
        Long count = checkSize(t);
        ExcelWriter excelWriter = EasyExcel
                .write(response.getOutputStream())
                .head(head)
                .registerWriteHandler(writeHandler)
                .build();
        responseExcel(sheetName, t, count, excelWriter);
        ExcelUtil.setResponse(response, fileName, excelTypeEnum);
        excelWriter.finish();
    }

    /**
     * 使用模板文件导出
     *
     * @param response         返回
     * @param fileName         文件名称
     * @param sheetName        sheet名称
     * @param templateLocation 模板位置 例：classpath:xxx.xlsx
     * @param t                入参泛型
     * @param excelTypeEnum    {@link ExcelTypeEnum}
     */
    @SneakyThrows
    public void exportWithTemplate(HttpServletResponse response,
                                   String fileName, String sheetName,
                                   String templateLocation, T t,
                                   ExcelTypeEnum excelTypeEnum) {
        Long count = checkSize(t);
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(templateLocation);
        ExcelWriter excelWriter = EasyExcel
                .write(response.getOutputStream())
                .withTemplate(resource.getInputStream())
                .build();
        responseExcel(sheetName, t, count, excelWriter);
        ExcelUtil.setResponse(response, fileName, excelTypeEnum);
        excelWriter.finish();
    }

    /**
     * 导出excel
     *
     * @param sheetName sheet名称
     * @param t         入参泛型
     */
    private void responseExcel(String sheetName, T t, Long count, ExcelWriter excelWriter) {
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        if (count <= singleLimit) {
            List<K> dataWithIndex = exportExcelService.getDataWithIndex(t, 0L, singleLimit);
            excelWriter.write(dataWithIndex, writeSheet);
            dataWithIndex = new ArrayList<>();
        } else {
            BigDecimal bd = new BigDecimal(count);
            long pageSize = bd.divide(new BigDecimal(batchSize), RoundingMode.UP).longValue();
            for (int i = 1; i <= pageSize; i++) {
                List<K> dataWithIndex = exportExcelService.getDataWithIndex(t, (i - 1) * batchSize, batchSize);
                excelWriter.write(dataWithIndex, writeSheet);
                dataWithIndex = new ArrayList<>();
            }
        }
    }

    private Long checkSize(T t) {
        Long count = exportExcelService.count(t);
        if (count > batchLimit) {
            throw new ExcelException("导出数量过大");
        }
        return count;
    }

    /**
     * 创建表格样式
     *
     * @return 默认表格样式
     */
    private WriteHandler defaultWriteHandler() {
        return new LongestMatchColumnWidthStyleStrategy();
    }
}
