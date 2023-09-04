package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 黑名单拦截日志 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:49.983
 */
@Data
public class BlockLogExcelVo {

    /**
     * 拦截日志id
     */
    @ExcelProperty(value = "拦截日志id")
    private Long blockId;

    /**
     * 拦截ip
     */
    @ExcelProperty(value = "拦截ip")
    private String blockIp;

    /**
     * 被拦截请求URI
     */
    @ExcelProperty(value = "被拦截请求URI")
    private String requestUri;

    /**
     * 被拦截请求方法
     */
    @ExcelProperty(value = "被拦截请求方法")
    private String requestMethod;

    /**
     * 拦截时间
     */
    @ExcelProperty(value = "拦截时间")
    private LocalDateTime requestTime;

    /**
     * IP对应地址
     */
    @ExcelProperty(value = "IP对应地址")
    private String location;


}