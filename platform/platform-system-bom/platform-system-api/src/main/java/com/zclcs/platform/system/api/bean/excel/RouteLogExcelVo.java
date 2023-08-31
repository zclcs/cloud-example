package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 网关转发日志 Vo
 *
 * @author zclcs
 * @since 2023-01-10 10:40:09.958
 */
@Data
public class RouteLogExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 目标服务
     */
    @ExcelProperty(value = "目标服务")
    private String targetServer;

    /**
     * 请求时间
     */
    @ExcelProperty(value = "请求时间")
    private LocalDateTime requestTime;

    /**
     * 响应code
     */
    @ExcelProperty(value = "响应code")
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