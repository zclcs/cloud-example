package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

/**
 * 字典 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 20:03:54.686
 */
@Data
public class DictItemExcelVo {

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 字典key
     */
    @ExcelProperty(value = "字典key")
    private String dictName;

    /**
     * 父级字典值
     */
    @ExcelProperty(value = "父级字典值")
    private String parentValue;

    /**
     * 值
     */
    @ExcelProperty(value = "值")
    private String value;

    /**
     * 标签
     */
    @ExcelProperty(value = "标签")
    private String title;

    /**
     * 字典类型 @@system_dict_item.type
     */
    @ExcelProperty(value = "字典类型 @@system_dict_item.type")
    private String type;

    public void setType(String type) {
        this.type = DictCacheUtil.getDictTitle("system_dict_item.type", type);
    }

    /**
     * 是否系统字典 @@yes_no
     */
    @ExcelProperty(value = "是否系统字典 @@yes_no")
    private String whetherSystemDict;

    public void setWhetherSystemDict(String whetherSystemDict) {
        this.whetherSystemDict = DictCacheUtil.getDictTitle("yes_no", whetherSystemDict);
    }

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 排序（升序）
     */
    @ExcelProperty(value = "排序（升序）")
    private Integer sorted;

    /**
     * 是否禁用 @@yes_no
     */
    @ExcelProperty(value = "是否禁用 @@yes_no")
    private String isDisabled;

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = DictCacheUtil.getDictTitle("yes_no", isDisabled);
    }


}