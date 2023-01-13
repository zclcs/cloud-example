package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.platform.system.api.entity.Log;
import com.zclcs.platform.system.api.entity.ao.LogAo;
import com.zclcs.platform.system.api.entity.vo.LogVo;
import com.zclcs.platform.system.biz.mapper.LogMapper;
import com.zclcs.platform.system.biz.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户操作日志 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public BasePage<LogVo> findLogPage(BasePageAo basePageAo, LogVo logVo) {
        BasePage<LogVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<LogVo> queryWrapper = getQueryWrapper(logVo);
        // TODO 设置分页查询条件
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<LogVo> findLogList(LogVo logVo) {
        QueryWrapper<LogVo> queryWrapper = getQueryWrapper(logVo);
        // TODO 设置集合查询条件
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public LogVo findLog(LogVo logVo) {
        QueryWrapper<LogVo> queryWrapper = getQueryWrapper(logVo);
        // TODO 设置单个查询条件
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countLog(LogVo logVo) {
        QueryWrapper<LogVo> queryWrapper = getQueryWrapper(logVo);
        // TODO 设置统计查询条件
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<LogVo> getQueryWrapper(LogVo logVo) {
        QueryWrapper<LogVo> queryWrapper = new QueryWrapper<>();
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Log createLog(LogAo logAo) {
        Log log = new Log();
        BeanUtil.copyProperties(logAo, log);
        this.save(log);
        return log;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Log updateLog(LogAo logAo) {
        Log log = new Log();
        BeanUtil.copyProperties(logAo, log);
        this.updateById(log);
        return log;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLog(List<Long> ids) {
        this.removeByIds(ids);
    }
}
