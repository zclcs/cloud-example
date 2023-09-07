package com.zclcs.common.export.excel.starter.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.zclcs.common.export.excel.starter.annotation.ExcelSelect;
import com.zclcs.common.export.excel.starter.bean.ExcelSelectDataColumn;
import com.zclcs.common.export.excel.starter.handler.SelectDataSheetWriteHandler;
import com.zclcs.common.export.excel.starter.kit.ExcelException;
import com.zclcs.common.export.excel.starter.service.ExportExcelService;
import com.zclcs.common.export.excel.starter.utils.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author zclcs
 */
public class SimpleExportListener<T, K> {

    private final Field[] declaredFields;

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

    private final ExportExcelService<T, K> exportExcelService;

    private WriteHandler headHandler = new LongestMatchColumnWidthStyleStrategy();

    private WriteHandler selectHandler;

    public SimpleExportListener(ExportExcelService<T, K> exportExcelService, Field[] declaredFields) {
        this.exportExcelService = exportExcelService;
        this.declaredFields = declaredFields;
        selectHandler = new SelectDataSheetWriteHandler(resolveExcelSelect(declaredFields));
    }

    public SimpleExportListener(ExportExcelService<T, K> exportExcelService, Field[] declaredFields, WriteHandler headHandler, WriteHandler selectHandler) {
        this.exportExcelService = exportExcelService;
        this.declaredFields = declaredFields;
        this.headHandler = headHandler;
        this.selectHandler = selectHandler;
    }

    public SimpleExportListener(ExportExcelService<T, K> exportExcelService, Field[] declaredFields, Long singleLimit, Long batchSize, Long batchLimit) {
        this.exportExcelService = exportExcelService;
        this.declaredFields = declaredFields;
        this.singleLimit = singleLimit;
        this.batchSize = batchSize;
        this.batchLimit = batchLimit;
        selectHandler = new SelectDataSheetWriteHandler(resolveExcelSelect(declaredFields));
    }

    public SimpleExportListener(ExportExcelService<T, K> exportExcelService, Field[] declaredFields, Long singleLimit, Long batchSize, Long batchLimit, WriteHandler headHandler, WriteHandler selectHandler) {
        this.exportExcelService = exportExcelService;
        this.declaredFields = declaredFields;
        this.singleLimit = singleLimit;
        this.batchSize = batchSize;
        this.batchLimit = batchLimit;
        this.headHandler = headHandler;
        this.selectHandler = selectHandler;
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
     *
     * @param response      返回
     * @param fileName      文件名称
     * @param sheetName     sheet名称
     * @param clazz         类路径
     * @param t             入参泛型
     * @param excelTypeEnum {@link ExcelTypeEnum}
     */
    @SneakyThrows
    public void exportWithEntity(HttpServletResponse response, String fileName, String sheetName,
                                 Class<K> clazz, T t,
                                 ExcelTypeEnum excelTypeEnum) {
        Long count = checkSize(t);
        ExcelWriter excelWriter = EasyExcel
                .write(response.getOutputStream(), clazz)
                .registerWriteHandler(headHandler)
                .registerWriteHandler(selectHandler)
                .build();
        responseExcel(sheetName, t, count, excelWriter);
        ExcelUtil.setResponse(response, fileName, excelTypeEnum);
        excelWriter.finish();
    }


    /**
     * 动态表头导出
     *
     * @param response      返回
     * @param fileName      文件名称
     * @param sheetName     sheet名称
     * @param head          动态表头
     * @param t             入参泛型
     * @param excelTypeEnum {@link ExcelTypeEnum}
     */
    @SneakyThrows
    public void exportWithDynamicHead(HttpServletResponse response,
                                      String fileName, String sheetName,
                                      List<List<String>> head, T t,
                                      ExcelTypeEnum excelTypeEnum) {
        Long count = checkSize(t);
        ExcelWriter excelWriter = EasyExcel
                .write(response.getOutputStream())
                .head(head)
                .registerWriteHandler(headHandler)
                .registerWriteHandler(selectHandler)
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
            List<K> dataWithIndex = exportExcelService.getDataPaginateAs(t, 1L, singleLimit, count);
            excelWriter.write(dataWithIndex, writeSheet);
            dataWithIndex = new ArrayList<>();
        } else {
            BigDecimal bd = new BigDecimal(count);
            long pageSize = bd.divide(new BigDecimal(batchSize), RoundingMode.UP).longValue();
            for (int i = 1; i <= pageSize; i++) {
                List<K> dataWithIndex = exportExcelService.getDataPaginateAs(t, 1L, singleLimit, count);
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

    private <T> Map<Integer, ExcelSelectDataColumn> resolveExcelSelect(Field[] declaredFields) {
        Map<Integer, ExcelSelectDataColumn> selectedMap = new HashMap<>(10);
        List<Field> fields = Arrays.stream(declaredFields).filter(field -> !field.isAnnotationPresent(ExcelIgnore.class) && !Modifier.isStatic(field.getModifiers())).toList();
        AtomicInteger annotatedIndex = new AtomicInteger(0);
        AtomicInteger maxHeadLayers = new AtomicInteger(1);
        fields.forEach(f -> {
            ExcelSelect selected = f.getAnnotation(ExcelSelect.class);
            ExcelProperty property = f.getAnnotation(ExcelProperty.class);
            final int index = annotatedIndex.getAndIncrement();
            if (selected != null) {
                ExcelSelectDataColumn excelSelectedResolve;
                if (StrUtil.isNotEmpty(selected.parentColumn())) {
                    excelSelectedResolve = new ExcelSelectDataColumn<Map<String, List<String>>>();
                } else {
                    excelSelectedResolve = new ExcelSelectDataColumn<List<String>>();
                }
                final Object source = excelSelectedResolve.resolveSource(selected);
                final int headLayerCount = property != null ? property.value().length : 1;
                final String columName = property != null ? property.value()[headLayerCount - 1] : f.getName();
                maxHeadLayers.set(Math.max(headLayerCount, maxHeadLayers.get()));
                excelSelectedResolve.setParentColumn(selected.parentColumn());
                excelSelectedResolve.setColumn(columName);
                excelSelectedResolve.setSource(Objects.nonNull(source) ? source : Collections.emptyList());
                excelSelectedResolve.setFirstRow(Math.max(selected.firstRow(), headLayerCount));
                excelSelectedResolve.setLastRow(selected.lastRow());
                excelSelectedResolve.setColumnIndex(index);
                selectedMap.put(index, excelSelectedResolve);
            }
        });

        if (CollUtil.isNotEmpty(selectedMap)) {
            selectedMap.forEach((k, v) -> {
                v.setFirstRow(Math.max(v.getFirstRow(), maxHeadLayers.get()));
            });
            final Map<String, Integer> indexMap = selectedMap
                    .values()
                    .stream()
                    .collect(Collectors.toMap(ExcelSelectDataColumn::getColumn, ExcelSelectDataColumn::getColumnIndex));
            selectedMap.forEach((k, v) -> {
                if (indexMap.containsKey(v.getParentColumn())) {
                    v.setParentColumnIndex(indexMap.get(v.getParentColumn()));
                }
            });
        }
        return selectedMap;
    }
}
