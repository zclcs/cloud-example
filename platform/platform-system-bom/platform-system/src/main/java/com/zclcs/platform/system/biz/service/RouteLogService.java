package com.zclcs.platform.system.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.platform.system.api.entity.RouteLog;
import com.zclcs.platform.system.api.entity.ao.RouteLogAo;
import com.zclcs.platform.system.api.entity.vo.RouteLogVo;

import java.util.List;

/**
 * 网关转发日志 Service接口
 *
 * @author zclcs
 * @date 2023-01-10 10:40:09.958
 */
public interface RouteLogService extends IService<RouteLog> {

    /**
     * 查询（分页）
     *
     * @param basePageAo basePageAo
     * @param routeLogVo routeLogVo
     * @return BasePage<RouteLogVo>
     */
    BasePage<RouteLogVo> findRouteLogPage(BasePageAo basePageAo, RouteLogVo routeLogVo);

    /**
     * 查询（所有）
     *
     * @param routeLogVo routeLogVo
     * @return List<RouteLogVo>
     */
    List<RouteLogVo> findRouteLogList(RouteLogVo routeLogVo);

    /**
     * 查询（单个）
     *
     * @param routeLogVo routeLogVo
     * @return RouteLogVo
     */
    RouteLogVo findRouteLog(RouteLogVo routeLogVo);

    /**
     * 统计
     *
     * @param routeLogVo routeLogVo
     * @return RouteLogVo
     */
    Integer countRouteLog(RouteLogVo routeLogVo);

    /**
     * 新增
     *
     * @param routeLogAo routeLogAo
     * @return RouteLog
     */
//    @Async(MyConstant.ASYNC_POOL)
    RouteLog createRouteLog(RouteLogAo routeLogAo);

    /**
     * 删除
     *
     * @param ids ids
     */
    void deleteRouteLog(List<Long> ids);

}
