package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.platform.system.api.bean.vo.RouteLogVo;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 网关转发日志 Vo
 *
 * @author zclcs
 * @since 2023-01-10 10:40:09.958
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
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

    public static RouteLogExcelVo convertToRouteLogExcelVo(RouteLogVo item) {
        if (item == null) {
            return null;
        }
        return RouteLogExcelVo.builder()
                .routeIp(item.getRouteIp())
                .requestUri(item.getRequestUri())
                .targetUri(item.getTargetUri())
                .requestMethod(item.getRequestMethod())
                .targetServer(item.getTargetServer())
                .requestTime(item.getRequestTime())
                .code(item.getCode())
                .time(item.getTime())
                .location(item.getLocation())
                .build();
    }

    public static List<RouteLogExcelVo> convertToList(List<RouteLogVo> items) {
        if (items == null) {
            return null;
        }
        List<RouteLogExcelVo> result = new ArrayList<>();
        for (RouteLogVo item : items) {
            RouteLogExcelVo routeLogExcelVo = convertToRouteLogExcelVo(item);
            if (routeLogExcelVo != null) {
                result.add(routeLogExcelVo);
            }
        }
        return result;
    }

}