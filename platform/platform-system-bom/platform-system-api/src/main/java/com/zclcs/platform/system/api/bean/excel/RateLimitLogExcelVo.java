package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 限流拦截日志 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:54.652
 */
@Data
public class RateLimitLogExcelVo {

    /**
     * 限流日志id
     */
    @ExcelProperty(value = "限流日志id")
    private Long rateLimitLogId;

    /**
     * 被拦截请求IP
     */
    @ExcelProperty(value = "被拦截请求IP")
    private String rateLimitLogIp;

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