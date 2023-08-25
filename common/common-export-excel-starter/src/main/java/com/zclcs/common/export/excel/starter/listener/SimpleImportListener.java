package com.zclcs.common.export.excel.starter.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ListUtils;
import com.zclcs.common.export.excel.starter.service.ImportExcelService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SimpleImportListener<T> extends AnalysisEventListener<Map<Integer, String>> {

    private ImportExcelService<T> importExcelService;

    /**
     * 每隔100条存储数据库，然后清理list ，方便内存回收
     */
    private int batchSize = 100;
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(batchSize);
    @Getter
    private Map<Integer, CellData<?>> error = new HashMap<>();

    public SimpleImportListener(ImportExcelService<T> importExcelService) {
        this.importExcelService = importExcelService;
    }

    public SimpleImportListener(ImportExcelService<T> importExcelService, int batchSize) {
        this.importExcelService = importExcelService;
        this.batchSize = batchSize;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage(), exception);
        Integer rowIndex = context.readSheetHolder().getRowIndex();
        ReadCellData<?> tempCellData = context.readSheetHolder().getTempCellData();
        error.put(rowIndex, tempCellData);
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.debug("解析到一行数据 {}", data.toString());
        T bean = this.importExcelService.toBean(data);
        cachedDataList.add(bean);
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
