package com.zclcs.platform.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.aop.ao.LogAo;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.mybatis.flex.utils.PredicateUtil;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.platform.system.api.bean.entity.Log;
import com.zclcs.platform.system.api.bean.vo.LogVo;
import com.zclcs.platform.system.mapper.LogMapper;
import com.zclcs.platform.system.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zclcs.platform.system.api.bean.entity.table.LogTableDef.LOG;

/**
 * 用户操作日志 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:40:01.346
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    private final Ip2regionSearcher ip2regionSearcher;

    @Override
    public BasePage<LogVo> findLogPage(BasePageAo basePageAo, LogVo logVo) {
        QueryWrapper queryWrapper = getQueryWrapper(logVo);
        Page<LogVo> logVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, LogVo.class);
        return new BasePage<>(logVoPage);
    }

    @Override
    public List<LogVo> findLogList(LogVo logVo) {
        QueryWrapper queryWrapper = getQueryWrapper(logVo);
        return this.mapper.selectListByQueryAs(queryWrapper, LogVo.class);
    }

    @Override
    public LogVo findLog(LogVo logVo) {
        QueryWrapper queryWrapper = getQueryWrapper(logVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, LogVo.class);
    }

    @Override
    public Long countLog(LogVo logVo) {
        QueryWrapper queryWrapper = getQueryWrapper(logVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(LogVo logVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        LOG.ID,
                        LOG.USERNAME,
                        LOG.OPERATION,
                        LOG.TIME,
                        LOG.METHOD,
                        LOG.PARAMS,
                        LOG.IP,
                        LOG.CREATE_AT,
                        LOG.LOCATION
                )
                .where(LOG.USERNAME.eq(logVo.getUsername(), If::hasText))
                .and(LOG.OPERATION.likeRight(logVo.getOperation(), If::hasText))
                .and(LOG.LOCATION.likeRight(logVo.getLocation(), If::hasText))
                .and(LOG.CREATE_AT.between(
                        logVo.getCreateAtFrom(),
                        logVo.getCreateAtTo(),
                        PredicateUtil.localDateBothNotNull
                ))
                .orderBy(LOG.CREATE_AT.desc())
        ;
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Log createLog(LogAo logAo) {
        Log log = genLog(logAo);
        this.save(log);
        return log;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLogBatch(List<LogAo> logAos) {
        List<Log> logs = new ArrayList<>();
        for (LogAo logAo : logAos) {
            logs.add(genLog(logAo));
        }
        this.saveBatch(logs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLog(List<Long> ids) {
        this.removeByIds(ids);
    }

    private Log genLog(LogAo logAo) {
        Log log = new Log();
        List<String> split = StrUtil.split(logAo.getIp(), Strings.COMMA);
        String ip = split.get(0);
        log.setIp(ip);
        log.setUsername(logAo.getUsername());
        log.setTime(logAo.getTime());
        log.setOperation(logAo.getOperation());
        log.setMethod(logAo.getClassName() + "." + logAo.getMethodName() + "()");
        log.setParams(Optional.ofNullable(logAo.getParams()).orElse(""));
        log.setLocation(ip2regionSearcher.getAddress(ip));
        return log;
    }
}
