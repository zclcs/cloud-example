package com.zclcs.cloud.lib.excel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.cloud.lib.excel.annotation.ExcelDict;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.util.List;

public class CascadeWriteHandler implements SheetWriteHandler {

    private final Field[] declaredFields;

    public CascadeWriteHandler(Field[] declaredFields) {
        this.declaredFields = declaredFields;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        //获取工作簿
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook book = writeWorkbookHolder.getWorkbook();
        //创建一个专门用来存放地区信息的隐藏sheet页
        //因此不能在现实页之前创建，否则无法隐藏。
        Sheet hideSheet = book.createSheet("site");
        book.setSheetHidden(book.getSheetIndex(hideSheet), true);
        // 将具体的数据写入到每一行中，行开头为父级区域，后面是子区域。
        int rowId = 0;
        Row proviRow = hideSheet.createRow(rowId++);
        proviRow.createCell(0).setCellValue("大类列表");
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            ExcelDict annotation = declaredField.getAnnotation(ExcelDict.class);
            if (annotation != null && annotation.isTree()) {
                String dictName = annotation.value();
                List<DictItemCacheVo> dictByDictNameAndParentValue = DictCacheUtil.getDictByDictNameAndParentValue(dictName, CommonCore.TOP_PARENT_CODE);
                for (int j = 0; j < dictByDictNameAndParentValue.size(); j++) {
                    DictItemCacheVo dictItemCacheVo = dictByDictNameAndParentValue.get(j);
                    Cell proviCell = proviRow.createCell(i + 1);
                    proviCell.setCellValue(dictItemCacheVo.getTitle());
                    String parentValue = dictItemCacheVo.getValue();
                    getC(dictName, book, hideSheet, rowId, 1, annotation.treeDeep(), parentValue);
                }
                ///开始设置（大类小类）下拉框
                DataValidationHelper dvHelper = sheet.getDataValidationHelper();
                // 大类规则
                DataValidationConstraint expConstraint = dvHelper.createExplicitListConstraint(dictByDictNameAndParentValue.stream().map(DictItemCacheVo::getTitle).toList().toArray(new String[]{}));
                CellRangeAddressList expRangeAddressList = new CellRangeAddressList(1, 999, 0, 0);
                setValidation(sheet, dvHelper, expConstraint, expRangeAddressList, "提示", "你输入的值未在备选列表中，请下拉选择合适的值");

                // 小类规则(各单元格按个设置)
                // "INDIRECT($A$" + 2 + ")" 表示规则数据会从名称管理器中获取key与单元格 A2 值相同的数据，如果A2是浙江省，那么此处就是浙江省下面的市
                // 为了让每个单元格的公式能动态适应，使用循环挨个给公式。
                // 循环几次，就有几个单元格生效，次数要和上面的大类影响行数一一对应，要不然最后几个没对上的单元格实现不了级联
                for (int j = 2; j < 1000; j++) {
                    CellRangeAddressList rangeAddressList = new CellRangeAddressList(i - 1, i - 1, 1, 1);
                    DataValidationConstraint formula = dvHelper.createFormulaListConstraint("INDIRECT($A$" + i + ")");
                    setValidation(sheet, dvHelper, formula, rangeAddressList, "提示", "你输入的值未在备选列表中，请下拉选择合适的值");
                }
            }
        }
    }

    private void getC(String dictName, Workbook book, Sheet hideSheet, int rowId, int treeDeep, int setTreeDeep, String parentValue) {
        List<DictItemCacheVo> ch = DictCacheUtil.getDictByDictNameAndParentValue(dictName, parentValue);
        Row row = hideSheet.createRow(rowId++);
        row.createCell(0).setCellValue(treeDeep + "列表");
        for (int i = 0; i < ch.size(); i++) {
            DictItemCacheVo itemCacheVo = ch.get(i);
            Cell cell = row.createCell(i + 1);
            cell.setCellValue(itemCacheVo.getTitle());
            if (treeDeep < setTreeDeep) {
                treeDeep++;
                getC(dictName, book, hideSheet, rowId, treeDeep, setTreeDeep, itemCacheVo.getValue());
            }
        }
        // 添加名称管理器
        String range = getRange(1, rowId, ch.size());
        Name name = book.createName();
        name.setNameName(treeDeep + "列表");
        String formula = "site!" + range;
        name.setRefersToFormula(formula);
    }

    /**
     * 设置验证规则
     *
     * @param sheet       sheet对象
     * @param helper      验证助手
     * @param constraint  createExplicitListConstraint
     * @param addressList 验证位置对象
     * @param msgHead     错误提示头
     * @param msgContext  错误提示内容
     */
    private void setValidation(Sheet sheet, DataValidationHelper helper, DataValidationConstraint constraint, CellRangeAddressList addressList, String msgHead, String msgContext) {
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        dataValidation.setShowErrorBox(true);
        dataValidation.setSuppressDropDownArrow(true);
        dataValidation.createErrorBox(msgHead, msgContext);
        sheet.addValidationData(dataValidation);
    }

    /**
     * @param offset   偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowId    第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
     * @author denggonghai 2016年8月31日 下午5:17:49
     */
    public String getRange(int offset, int rowId, int colCount) {
        char start = (char) ('A' + offset);
        if (colCount <= 25) {
            char end = (char) (start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix = 'A';
            // 26-51之间，包括边界（仅两次字母表计算）
            if ((colCount - 25) / 26 == 0 || colCount == 51) {
                // 边界值
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                }
            } else {// 51以上
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
        }
    }
}
