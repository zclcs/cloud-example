package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 网关转发日志 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-01 20:09:35.391
 */
@Data
public class RouteLogExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关转发日志id
     */
    @ExcelProperty(value = "网关转发日志id")
    private Long routeId;

    /**
     * 请求ip
     */
    @ExcelProperty(value = "请求ip")
    private String routeIp;

    /**
     * 请求uri
     */
    @ExcelProperty(value = "请求uri")
    private String requestUri;

    /**
     * 目标uri
     */
    @ExcelProperty(value = "目标uri")
    private String targetUri;

    /**
     * 请求方法
     */
    @ExcelProperty(value = "请求方法")
    private String requestMethod;

    /**
     * 请求时间
     */
    @ExcelProperty(value = "请求时间")
    private LocalDateTime requestTime;

    /**
     * 目标服务
     */
    @ExcelProperty(value = "目标服务")
    private String targetServer;

    /**
     * 响应码
     */
    @ExcelProperty(value = "响应码")
    private String code;

    /**
     * 响应时间
     */
    @ExcelProperty(value = "响应时间")
    private Long time;

    /**
     * ip对应地址
     */
    @ExcelProperty(value = "ip对应地址")
    private String location;


}