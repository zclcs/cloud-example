package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.RouteLog;
import com.zclcs.platform.system.api.entity.ao.RouteLogAo;
import com.zclcs.platform.system.api.entity.vo.RouteLogVo;
import com.zclcs.platform.system.biz.mapper.RouteLogMapper;
import com.zclcs.platform.system.biz.service.RouteLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class RouteLogServiceImpl extends ServiceImpl<RouteLogMapper, RouteLog> implements RouteLogService {

    @Override
    public BasePage<RouteLogVo> findRouteLogPage(BasePageAo basePageAo, RouteLogVo routeLogVo) {
        BasePage<RouteLogVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<RouteLogVo> findRouteLogList(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public RouteLogVo findRouteLog(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countRouteLog(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = getQueryWrapper(routeLogVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<RouteLogVo> getQueryWrapper(RouteLogVo routeLogVo) {
        QueryWrapper<RouteLogVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RouteLog createRouteLog(RouteLogAo routeLogAo) {
        RouteLog routeLog = new RouteLog();
        BeanUtil.copyProperties(routeLogAo, routeLog);
        this.save(routeLog);
        return routeLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RouteLog updateRouteLog(RouteLogAo routeLogAo) {
        RouteLog routeLog = new RouteLog();
        BeanUtil.copyProperties(routeLogAo, routeLog);
        this.updateById(routeLog);
        return routeLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRouteLog(List<Long> ids) {
        this.removeByIds(ids);
    }
}
