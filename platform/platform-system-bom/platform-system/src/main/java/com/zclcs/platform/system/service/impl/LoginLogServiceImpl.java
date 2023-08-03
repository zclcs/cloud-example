package com.zclcs.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.mybatis.plus.utils.QueryWrapperUtil;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.platform.system.api.bean.ao.LoginLogAo;
import com.zclcs.platform.system.api.bean.entity.LoginLog;
import com.zclcs.platform.system.api.bean.vo.LoginLogVo;
import com.zclcs.platform.system.mapper.LoginLogMapper;
import com.zclcs.platform.system.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录日志 Service实现
 *
 * @author zclcs
 * @date 2023-01-10 10:39:57.150
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    private final Ip2regionSearcher ip2regionSearcher;

    @Override
    public BasePage<LoginLogVo> findLoginLogPage(BasePageAo basePageAo, LoginLogVo loginLogVo) {
        BasePage<LoginLogVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<LoginLogVo> queryWrapper = getQueryWrapper(loginLogVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<LoginLogVo> findLoginLogList(LoginLogVo loginLogVo) {
        QueryWrapper<LoginLogVo> queryWrapper = getQueryWrapper(loginLogVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public LoginLogVo findLoginLog(LoginLogVo loginLogVo) {
        QueryWrapper<LoginLogVo> queryWrapper = getQueryWrapper(loginLogVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countLoginLog(LoginLogVo loginLogVo) {
        QueryWrapper<LoginLogVo> queryWrapper = getQueryWrapper(loginLogVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<LoginLogVo> getQueryWrapper(LoginLogVo loginLogVo) {
        QueryWrapper<LoginLogVo> queryWrapper = new QueryWrapper<>();
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sll.ip", loginLogVo.getIp());
        QueryWrapperUtil.likeNotBlank(queryWrapper, "sll.username", loginLogVo.getUsername());
        QueryWrapperUtil.eqNotBlank(queryWrapper, "sll.login_type", loginLogVo.getLoginType());
        QueryWrapperUtil.betweenDateAddTimeNotBlank(queryWrapper,
                "sll.login_time", loginLogVo.getLoginTimeFrom(), loginLogVo.getLoginTimeTo());
        queryWrapper.orderByDesc("sll.login_time");
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLoginLog(LoginLogAo loginLogAo) {
        LoginLog loginLog = new LoginLog();
        BeanUtil.copyProperties(loginLogAo, loginLog);
        loginLog.setLocation(ip2regionSearcher.getAddress(loginLogAo.getIp()));
        this.save(loginLog);
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
