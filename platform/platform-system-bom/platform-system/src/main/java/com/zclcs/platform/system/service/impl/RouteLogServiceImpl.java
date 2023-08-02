package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.platform.system.api.bean.ao.RouteLogAo;
import com.zclcs.platform.system.api.bean.entity.RouteLog;
import com.zclcs.platform.system.api.bean.vo.RouteLogVo;
import com.zclcs.platform.system.mapper.RouteLogMapper;
import com.zclcs.platform.system.service.RouteLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public RouteLogVo findRouteLog(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRouteLog(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<RouteLogVo> getQueryWrapper(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "srl.route_ip", routeLogVo.getRouteIp());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "srl.target_server", routeLogVo.getTargetServer());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "srl.request_method", routeLogVo.getRequestMethod());
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
