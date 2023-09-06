package com.zclcs.cloud.lib.excel.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.cloud.lib.excel.annotation.ExcelDict;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropDownWriteHandler implements SheetWriteHandler {

    private final Field[] declaredFields;

    public DropDownWriteHandler(Field[] declaredFields) {
        this.declaredFields = declaredFields;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 获取Sheet表
        Sheet sheet = writeSheetHolder.getSheet();
        //设置下拉框
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        // 定义一个map key是需要添加下拉框的列的index value是下拉框数据
        Map<Integer, String[]> mapDropDown = new HashMap<>(3);
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            ExcelDict annotation = declaredField.getAnnotation(ExcelDict.class);
            if (annotation != null && !annotation.isTree()) {
                List<DictItemCacheVo> dictByDictName = DictCacheUtil.getDictByDictName(annotation.value());
                if (CollectionUtil.isNotEmpty(dictByDictName)) {
                    String[] array = dictByDictName.stream().map(DictItemCacheVo::getTitle).toList().toArray(new String[0]);
                    //下拉选在Excel中对应的列
                    mapDropDown.put(i, array);
                }
            }
        }
        for (Map.Entry<Integer, String[]> entry : mapDropDown.entrySet()) {
            // 起始行、终止行、起始列、终止列  起始行为1即表示表头不设置
            CellRangeAddressList addressList = new CellRangeAddressList(1, 999, entry.getKey(), entry.getKey());
            //创建显式列表约束
            DataValidationConstraint constraint = dvHelper.createExplicitListConstraint(entry.getValue());
            // 指定行列约束以及错误信息
            DataValidation dataValidation = dvHelper.createValidation(constraint, addressList);
            dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            dataValidation.setShowErrorBox(true);
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.createErrorBox("提示", "你输入的值未在备选列表中，请下拉选择合适的值");
            sheet.addValidationData(dataValidation);
        }
    }
}

