package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.platform.system.api.bean.entity.RouteLog;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 网关转发日志 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:40:09.958
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RouteLogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网关转发日志id
     */
    private Long routeId;

    /**
     * 请求ip
     */
    private String routeIp;

    /**
     * 请求uri
     */
    private String requestUri;

    /**
     * 目标uri
     */
    private String targetUri;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 目标服务
     */
    private String targetServer;

    /**
     * 请求时间
     */
    private LocalDateTime requestTime;

    /**
     * 请求时间起
     */
    private LocalDate requestTimeFrom;

    /**
     * 请求时间起终
     */
    private LocalDate requestTimeTo;

    /**
     * 响应code
     */
    private String code;

    /**
     * 响应时间
     */
    private Long time;

    /**
     * ip对应地址
     */
    private String location;

    public static RouteLogVo convertToRouteLogVo(RouteLog item) {
        if (item == null) {
            return null;
        }
        RouteLogVo build = RouteLogVo.builder()
                .routeId(item.getRouteId())
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
        build.setCreateAt(item.getCreateAt());
        build.setUpdateBy(item.getUpdateBy());
        return build;
    }

    public static List<RouteLogVo> convertToList(List<RouteLog> items) {
        if (items == null) {
            return null;
        }
        List<RouteLogVo> result = new ArrayList<>();
        for (RouteLog item : items) {
            RouteLogVo routeLogVo = convertToRouteLogVo(item);
            if (routeLogVo != null) {
                result.add(routeLogVo);
            }
        }
        return result;
    }


}