package com.zclcs.common.export.excel.starter.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author zclcs
 */
public interface ImportExcelService<T, R> {

    /**
     * 读到的一行excel数据转换成beanVo
     *
     * @param cellData excel数据
     * @return beanVo
     */
    R toExcelVo(Map<String, String> cellData);

    /**
     * beanVo转换成实体
     *
     * @param excelVo beanVo
     * @return 实体
     */
    T toBean(R excelVo);

    /**
     * 保存到数据库
     *
     * @param t 实体集合
     */
    void saveBeans(List<T> t);

    /**
     * 字符串不为空字符则转化为Long，否则返回null
     *
     * @param excelData 表格数据
     * @return Long类型表格数据
     */
    default Long parseLong(String excelData) {
        return StrUtil.isNotBlank(excelData) ? Long.valueOf(excelData) : null;
    }

    /**
     * 字符串不为空字符则转化为LocalDate，否则返回null
     *
     * @param excelData 表格数据
     * @return LocalDate类型表格数据
     */
    default LocalDate parseLocalDate(String excelData) {
        return StrUtil.isNotBlank(excelData) ? LocalDate.parse(excelData, DatePattern.NORM_DATE_FORMATTER) : null;
    }

    /**
     * 字符串不为空字符则转化为LocalDateTime，否则返回null
     *
     * @param excelData 表格数据
     * @return LocalDateTime类型表格数据
     */
    default LocalDateTime parseLocalDateTime(String excelData) {
        return StrUtil.isNotBlank(excelData) ? LocalDateTime.parse(excelData, DatePattern.NORM_DATETIME_FORMATTER) : null;
    }

    /**
     * 字符串不为空字符则转化为Integer，否则返回null
     *
     * @param excelData 表格数据
     * @return Integer类型表格数据
     */
    default Integer parseInteger(String excelData) {
        return StrUtil.isNotBlank(excelData) ? Integer.valueOf(excelData) : null;
    }

    /**
     * 字符串不为空字符则转化为Double，否则返回null
     *
     * @param excelData 表格数据
     * @return Double类型表格数据
     */
    default Double parseDouble(String excelData) {
        return StrUtil.isNotBlank(excelData) ? Double.valueOf(excelData) : null;
    }

    /**
     * 字符串不为空字符则转化为Byte，否则返回null
     *
     * @param excelData 表格数据
     * @return Byte类型表格数据
     */
    default Byte parseByte(String excelData) {
        return StrUtil.isNotBlank(excelData) ? Byte.valueOf(excelData) : null;
    }

    /**
     * 字符串不为空字符则转化为BigDecimal，否则返回null
     *
     * @param excelData 表格数据
     * @return BigDecimal类型表格数据
     */
    default BigDecimal parseBigDecimal(String excelData) {
        return StrUtil.isNotBlank(excelData) ? new BigDecimal(excelData) : null;
    }

}
