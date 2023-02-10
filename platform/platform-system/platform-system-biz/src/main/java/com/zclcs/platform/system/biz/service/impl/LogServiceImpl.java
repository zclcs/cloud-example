package com.zclcs.platform.system.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.utils.AddressUtil;
import com.zclcs.common.datasource.starter.utils.QueryWrapperUtil;
import com.zclcs.platform.system.api.entity.Log;
import com.zclcs.platform.system.api.entity.vo.LogVo;
import com.zclcs.platform.system.biz.mapper.LogMapper;
import com.zclcs.platform.system.biz.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 用户操作日志 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:40:01.346
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public BasePage<LogVo> findLogPage(BasePageAo basePageAo, LogVo logVo) {
        BasePage<LogVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<LogVo> queryWrapper = getQueryWrapper(logVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<LogVo> findLogList(LogVo logVo) {
        QueryWrapper<LogVo> queryWrapper = getQueryWrapper(logVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public LogVo findLog(LogVo logVo) {
        QueryWrapper<LogVo> queryWrapper = getQueryWrapper(logVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countLog(LogVo logVo) {
        QueryWrapper<LogVo> queryWrapper = getQueryWrapper(logVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<LogVo> getQueryWrapper(LogVo logVo) {
        QueryWrapper<LogVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sl.create_time");
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sl.username", logVo.getUsername());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sl.operation", logVo.getOperation());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sl.location", logVo.getLocation());
        QueryWrapperUtil.betweenDateAddTimeNotBlank(queryWrapper, "sl.create_at", logVo.getCreateTimeFrom(), logVo.getCreateTimeTo());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLog(String className, String methodName, String params, String ip, String operation, String username, long start) {
        Log log = new Log();
        log.setIp(ip);
        log.setUsername(username);
        log.setTime(BigDecimal.valueOf(System.currentTimeMillis() - start));
        log.setOperation(operation);
        log.setMethod(className + "." + methodName + "()");
        log.setParams(Optional.ofNullable(params).orElse(""));
        log.setLocation(AddressUtil.getCityInfo(ip));
        this.save(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLog(List<Long> ids) {
        this.removeByIds(ids);
    }
}
