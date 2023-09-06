package com.zclcs.common.export.excel.starter.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropDownWriteHandler implements SheetWriteHandler {

    /**
     * 下拉框显示的数值
     */
    List<String> dropDown;

    public DropDownWriteHandler(List<String> dropDown) {
        this.dropDown = dropDown;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 开始设置 男/女下拉框
        // 定义一个map key是需要添加下拉框的列的index value是下拉框数据
        Map<Integer, String[]> mapDropDown = new HashMap<>(3);
        //性别下拉选项 {"男", "女"};
        String[] downArray = dropDown.toArray(new String[0]);
        //下拉选在Excel中对应的列
        mapDropDown.put(2, downArray);
        // 获取Sheet表
        Sheet sheet = writeSheetHolder.getSheet();
        //设置下拉框
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        for (Map.Entry<Integer, String[]> entry : mapDropDown.entrySet()) {
            // 起始行、终止行、起始列、终止列  起始行为1即表示表头不设置
            CellRangeAddressList addressList = new CellRangeAddressList(1, 999, entry.getKey(), entry.getKey());
            // 设置下拉框数据 (设置长度为0的数组会报错，所以这里需要判断)
            if (entry.getValue().length > 0) {
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
}

