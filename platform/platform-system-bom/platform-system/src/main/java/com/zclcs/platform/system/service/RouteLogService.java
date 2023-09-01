package com.zclcs.platform.system.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.platform.system.api.bean.ao.RouteLogAo;
import com.zclcs.platform.system.api.bean.entity.RouteLog;
import com.zclcs.platform.system.api.bean.vo.RouteLogVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 网关转发日志 Service接口
 *
 * @author zclcs
 * @since 2023-09-01 20:09:35.391
 */
public interface RouteLogService extends IService<RouteLog> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param routeLogVo {@link RouteLogVo}
     * @return {@link RouteLogVo}
     */
    BasePage<RouteLogVo> findRouteLogPage(BasePageAo basePageAo, RouteLogVo routeLogVo);

    /**
     * 查询（所有）
     *
     * @param routeLogVo {@link RouteLogVo}
     * @return {@link RouteLogVo}
     */
    List<RouteLogVo> findRouteLogList(RouteLogVo routeLogVo);

    /**
     * 导出
     *
     * @param routeLogVo {@link RouteLogVo}
     */
    void exportExcel(RouteLogVo routeLogVo);

    /**
     * 导入
     *
     * @param file 文件
     */
    void importExcel(MultipartFile file);

    /**
     * 查询（单个）
     *
     * @param routeLogVo {@link RouteLogVo}
     * @return {@link RouteLogVo}
     */
    RouteLogVo findRouteLog(RouteLogVo routeLogVo);

    /**
     * 统计
     *
     * @param routeLogVo {@link RouteLogVo}
     * @return 统计值
     */
    Long countRouteLog(RouteLogVo routeLogVo);

    /**
     * 新增
     *
     * @param routeLogAo {@link RouteLogAo}
     * @return {@link RouteLog}
     */
    RouteLog createRouteLog(RouteLogAo routeLogAo);

    /**
     * 批量新增
     *
     * @param routeLogAos {@link RouteLogAo}
     */
    void createRouteLogBatch(List<RouteLogAo> routeLogAos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteRouteLog(List<Long> ids);

}
