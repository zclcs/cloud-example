package com.zclcs.common.export.excel.starter.listener;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.util.ListUtils;
import com.zclcs.common.export.excel.starter.kit.Validators;
import com.zclcs.common.export.excel.starter.service.ImportExcelService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zclcs
 */
@Slf4j
public class SimpleImportListener<T, R> extends AnalysisEventListener<Map<Integer, String>> {

    private final ImportExcelService<T, R> importExcelService;

    private final Field[] declaredFields;

    /**
     * 每隔100条存储数据库，然后清理list ，方便内存回收
     */
    private int batchSize = 100;
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(batchSize);
    @Getter
    private Map<Integer, String> error = new HashMap<>();

    public SimpleImportListener(ImportExcelService<T, R> importExcelService, Field[] declaredFields) {
        this.importExcelService = importExcelService;
        this.declaredFields = declaredFields;
    }

    public SimpleImportListener(ImportExcelService<T, R> importExcelService, Field[] declaredFields, int batchSize) {
        this.importExcelService = importExcelService;
        this.declaredFields = declaredFields;
        this.batchSize = batchSize;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage(), exception);
        ReadRowHolder readRowHolder = context.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        error.put(rowIndex, exception.getMessage());
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.debug("解析到一行数据 {}", data.toString());
        Map<String, String> beanMap = new HashMap<>(declaredFields.length + 1);
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            beanMap.put(declaredField.getName(), data.get(i));
        }
        R excelVo = this.importExcelService.toExcelVo(beanMap);
        Set<ConstraintViolation<R>> violations = Validators.validate(excelVo);
        if (CollectionUtil.isNotEmpty(violations)) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<?> violation : violations) {
                Path path = violation.getPropertyPath();
                message.append(path.toString()).append(violation.getMessage()).append(StrUtil.COMMA);
            }
            message = new StringBuilder(message.substring(0, message.length() - 1));
            throw new ExcelAnalysisException(message.toString());
        }
        cachedDataList.add(this.importExcelService.toBean(excelVo));
        if (cachedDataList.size() >= batchSize) {
            this.importExcelService.saveBeans(cachedDataList);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(batchSize);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.importExcelService.saveBeans(cachedDataList);
    }

}
