package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.mybatis.flex.utils.PredicateUtil;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.platform.system.api.bean.ao.LoginLogAo;
import com.zclcs.platform.system.api.bean.entity.LoginLog;
import com.zclcs.platform.system.api.bean.vo.LoginLogVo;
import com.zclcs.platform.system.mapper.LoginLogMapper;
import com.zclcs.platform.system.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.zclcs.platform.system.api.bean.entity.table.LoginLogTableDef.LOGIN_LOG;

/**
 * 登录日志 Service实现
 *
 * @author zclcs
 * @since 2023-01-10 10:39:57.150
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    private final Ip2regionSearcher ip2regionSearcher;

    @Override
    public BasePage<LoginLogVo> findLoginLogPage(BasePageAo basePageAo, LoginLogVo loginLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(loginLogVo);
        Page<LoginLogVo> loginLogVoPage = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, LoginLogVo.class);
        return new BasePage<>(loginLogVoPage);
    }

    @Override
    public List<LoginLogVo> findLoginLogList(LoginLogVo loginLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(loginLogVo);
        return this.mapper.selectListByQueryAs(queryWrapper, LoginLogVo.class);
    }

    @Override
    public LoginLogVo findLoginLog(LoginLogVo loginLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(loginLogVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, LoginLogVo.class);
    }

    @Override
    public Long countLoginLog(LoginLogVo loginLogVo) {
        QueryWrapper queryWrapper = getQueryWrapper(loginLogVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(LoginLogVo loginLogVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        LOGIN_LOG.ID,
                        LOGIN_LOG.USERNAME,
                        LOGIN_LOG.LOGIN_TIME,
                        LOGIN_LOG.LOGIN_TYPE,
                        LOGIN_LOG.LOCATION,
                        LOGIN_LOG.IP,
                        LOGIN_LOG.SYSTEM,
                        LOGIN_LOG.BROWSER
                )
                .where(LOGIN_LOG.IP.likeRight(loginLogVo.getIp(), If::hasText))
                .and(LOGIN_LOG.USERNAME.likeRight(loginLogVo.getUsername(), If::hasText))
                .and(LOGIN_LOG.LOGIN_TYPE.eq(loginLogVo.getLoginType(), If::hasText))
                .and(LOGIN_LOG.LOGIN_TIME.between(
                        loginLogVo.getLoginTimeFrom(),
                        loginLogVo.getLoginTimeTo(),
                        PredicateUtil.localDateBothNotNull
                ))
                .orderBy(LOGIN_LOG.LOGIN_TIME.desc());
        ;
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginLog createLoginLog(LoginLogAo loginLogAo) {
        LoginLog loginLog = new LoginLog();
        BeanUtil.copyProperties(loginLogAo, loginLog);
        loginLog.setLocation(ip2regionSearcher.getAddress(loginLogAo.getIp()));
        this.save(loginLog);
        return loginLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLoginLogBatch(List<LoginLogAo> loginLogAos) {
        List<LoginLog> loginLogs = new ArrayList<>();
        for (LoginLogAo loginLogAo : loginLogAos) {
            LoginLog loginLog = new LoginLog();
            BeanUtil.copyProperties(loginLogAo, loginLog);
            loginLog.setLocation(ip2regionSearcher.getAddress(loginLogAo.getIp()));
            loginLogs.add(loginLog);
        }
        this.saveBatch(loginLogs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLoginLog(List<Long> ids) {
        this.removeByIds(ids);
    }
}
