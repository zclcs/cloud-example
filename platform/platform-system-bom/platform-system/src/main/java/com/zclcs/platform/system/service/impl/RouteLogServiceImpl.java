package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.mybatis.flex.utils.PredicateUtil;
import com.zclcs.common.export.excel.starter.kit.ExcelReadException;
import com.zclcs.common.export.excel.starter.listener.SimpleExportListener;
import com.zclcs.common.export.excel.starter.listener.SimpleImportListener;
import com.zclcs.common.export.excel.starter.service.ExportExcelService;
import com.zclcs.common.export.excel.starter.service.ImportExcelService;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.common.web.starter.utils.WebUtil;
import com.zclcs.platform.system.api.bean.ao.RouteLogAo;
import com.zclcs.platform.system.api.bean.entity.RouteLog;
import com.zclcs.platform.system.api.bean.excel.RouteLogExcelVo;
import com.zclcs.platform.system.api.bean.vo.RouteLogVo;
import com.zclcs.platform.system.mapper.RouteLogMapper;
import com.zclcs.platform.system.service.RouteLogService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zclcs.platform.system.api.bean.entity.table.RouteLogTableDef.ROUTE_LOG;

/**
 * 网关转发日志 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:40:09.958
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RouteLogServiceImpl extends ServiceImpl<RouteLogMapper, RouteLog> implements RouteLogService {

    private final Ip2regionSearcher ip2regionSearcher;

    @Override
    public BasePage<RouteLogVo> findRouteLogPage(BasePageAo basePageAo, RouteLogVo routeLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(routeLogVo);
        Page<RouteLogVo> paginate = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, RouteLogVo.class);
        return new BasePage<>(paginate);
    }

    @Override
    public List<RouteLogVo> findRouteLogList(RouteLogVo routeLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(routeLogVo);
        return this.mapper.selectListByQueryAs(queryWrapper, RouteLogVo.class);
    }

    @Override
    public void exportExcel(RouteLogVo routeLogVo) {
        SimpleExportListener<RouteLogVo, RouteLogExcelVo> routeLogVoRouteLogExcelVoSimpleExportListener = new SimpleExportListener<>(new ExportExcelService<>() {
            @Override
            public Long count(RouteLogVo t) {
                return countRouteLog(t);
            }

            @Override
            public List<RouteLogExcelVo> getDataPaginateAs(RouteLogVo routeLogVo, Long pageNum, Long pageSize, Long totalRows) {
                QueryWrapper queryWrapper = getQueryWrapper(routeLogVo);
                Page<RouteLogExcelVo> routeLogVoPage = mapper.paginateAs(pageNum, pageSize, totalRows, queryWrapper, RouteLogExcelVo.class);
                return routeLogVoPage.getRecords();
            }
        }, RouteLogExcelVo.class.getDeclaredFields());
        routeLogVoRouteLogExcelVoSimpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "网关转发日志", RouteLogExcelVo.class, routeLogVo);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile file) {
        SimpleImportListener<RouteLog, RouteLogExcelVo> routeLogSimpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public RouteLogExcelVo toExcelVo(Map<String, String> cellData) {
                RouteLogExcelVo routeLogExcelVo = new RouteLogExcelVo();
                routeLogExcelVo.setRouteIp(cellData.get("routeIp"));
                routeLogExcelVo.setRequestUri(cellData.get("requestUri"));
                routeLogExcelVo.setTargetUri(cellData.get("targetUri"));
                routeLogExcelVo.setRequestMethod(cellData.get("requestMethod"));
                routeLogExcelVo.setTargetServer(cellData.get("targetServer"));
                routeLogExcelVo.setRequestTime(DateUtil.parseLocalDateTime(cellData.get("requestTime")));
                routeLogExcelVo.setCode(cellData.get("code"));
                routeLogExcelVo.setTime(Long.valueOf(cellData.get("time")));
                routeLogExcelVo.setLocation(cellData.get("location"));
                return routeLogExcelVo;
            }

            @Override
            public RouteLog toBean(RouteLogExcelVo excelVo) {
                RouteLog routeLog = new RouteLog();
                BeanUtil.copyProperties(excelVo, routeLog);
                return routeLog;
            }

            @Override
            public void saveBeans(List<RouteLog> t) {
                saveBatch(t);
            }
        }, RouteLogExcelVo.class.getDeclaredFields(), 200);
        EasyExcel.read(file.getInputStream(), routeLogSimpleImportListener).sheet().doRead();
        Map<Integer, String> error = routeLogSimpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }

    @Override
    public RouteLogVo findRouteLog(RouteLogVo routeLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(routeLogVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, RouteLogVo.class);
    }

    @Override
    public Long countRouteLog(RouteLogVo routeLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(routeLogVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(RouteLogVo routeLogVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        ROUTE_LOG.ROUTE_ID,
                        ROUTE_LOG.ROUTE_IP,
                        ROUTE_LOG.REQUEST_URI,
                        ROUTE_LOG.TARGET_URI,
                        ROUTE_LOG.REQUEST_METHOD,
                        ROUTE_LOG.REQUEST_TIME,
                        ROUTE_LOG.TARGET_SERVER,
                        ROUTE_LOG.LOCATION,
                        ROUTE_LOG.CODE,
                        ROUTE_LOG.TIME,
                        ROUTE_LOG.CREATE_AT,
                        ROUTE_LOG.UPDATE_AT)
                .where(ROUTE_LOG.ROUTE_IP.like(routeLogVo.getRouteIp(), If::hasText))
                .and(ROUTE_LOG.TARGET_SERVER.like(routeLogVo.getTargetServer(), If::hasText))
                .and(ROUTE_LOG.REQUEST_METHOD.like(routeLogVo.getRequestMethod(), If::hasText))
                .and(ROUTE_LOG.REQUEST_TIME.between(
                        routeLogVo.getRequestTimeFrom(),
                        routeLogVo.getRequestTimeTo(),
                        PredicateUtil.localDateBothNotNull
                ))
                .orderBy(ROUTE_LOG.REQUEST_TIME.desc())
        ;
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RouteLog createRouteLog(RouteLogAo routeLogAo) {
        RouteLog routeLog = new RouteLog();
        BeanUtil.copyProperties(routeLogAo, routeLog);
        setRouteLog(routeLog);
        this.save(routeLog);
        return routeLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRouteLogBatch(List<RouteLogAo> routeLogAos) {
        List<RouteLog> routeLogs = new ArrayList<>();
        for (RouteLogAo routeLogAo : routeLogAos) {
            RouteLog routeLog = new RouteLog();
            BeanUtil.copyProperties(routeLogAo, routeLog);
            setRouteLog(routeLog);
            routeLogs.add(routeLog);
        }
        this.saveBatch(routeLogs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRouteLog(List<Long> ids) {
        this.removeByIds(ids);
    }

    private void setRouteLog(RouteLog routeLog) {
        if (StrUtil.isNotBlank(routeLog.getRouteIp())) {
            routeLog.setLocation(ip2regionSearcher.getAddress(routeLog.getRouteIp()));
        }
    }
}
