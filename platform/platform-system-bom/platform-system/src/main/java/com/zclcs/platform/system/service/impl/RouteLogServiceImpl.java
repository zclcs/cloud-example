package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.data.CellData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 网关转发日志 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:40:09.958
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RouteLogServiceImpl extends ServiceImpl<RouteLogMapper, RouteLog> implements RouteLogService {

    private final Ip2regionSearcher ip2regionSearcher;

    @Override
    public BasePage<RouteLogVo> findRouteLogPage(BasePageAo basePageAo, RouteLogVo routeLogVo) {
        BasePage<RouteLogVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RouteLogVo> findRouteLogList(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public void exportExcel(RouteLogVo routeLogVo) {
        SimpleExportListener<RouteLogVo, RouteLogExcelVo> routeLogVoRouteLogExcelVoSimpleExportListener = new SimpleExportListener<>(new ExportExcelService<>() {
            @Override
            public Long count(RouteLogVo t) {
                return countRouteLog(t);
            }

            @Override
            public List<RouteLogExcelVo> getDataWithIndex(RouteLogVo t, Long startIndex, Long endIndex) {
                QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(t);
                queryWrapper.last("limit " + startIndex + ", " + endIndex);
                List<RouteLogVo> listVo = baseMapper.findListVo(queryWrapper);
                return RouteLogExcelVo.convertToList(listVo);
            }
        });
        routeLogVoRouteLogExcelVoSimpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "网关转发日志", RouteLogExcelVo.class, routeLogVo);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile file) {
        SimpleImportListener<RouteLog> routeLogSimpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {
            @Override
            public RouteLog toBean(Map<Integer, String> cellData) {
                RouteLog routeLog = new RouteLog();
                routeLog.setRouteIp(cellData.get(0));
                routeLog.setRequestUri(cellData.get(1));
                routeLog.setTargetUri(cellData.get(2));
                routeLog.setRequestMethod(cellData.get(3));
                routeLog.setTargetServer(cellData.get(4));
                routeLog.setRequestTime(DateUtil.parseLocalDateTime(cellData.get(5)));
                routeLog.setCode(cellData.get(6));
                routeLog.setTime(Long.valueOf(cellData.get(7)));
                routeLog.setLocation(cellData.get(8));
                return routeLog;
            }

            @Override
            public void saveBeans(List<RouteLog> t) {
                saveBatch(t);
            }
        }, 200);
        EasyExcel.read(file.getInputStream(), routeLogSimpleImportListener).sheet().doRead();
        Map<Integer, CellData<?>> error = routeLogSimpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }

    @Override
    public RouteLogVo findRouteLog(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Long countRouteLog(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<RouteLogVo> getQueryWrapper(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeRightNotBlank(queryWrapper, "srl.route_ip", routeLogVo.getRouteIp());
        QueryWrapperUtil.likeRightNotBlank(queryWrapper, "srl.target_server", routeLogVo.getTargetServer());
        QueryWrapperUtil.likeRightNotBlank(queryWrapper, "srl.request_method", routeLogVo.getRequestMethod());
        QueryWrapperUtil.betweenDateAddTimeNotBlank(queryWrapper, "srl.request_time", routeLogVo.getRequestTimeFrom(), routeLogVo.getRequestTimeTo());
        queryWrapper.orderByDesc("srl.request_time");
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
