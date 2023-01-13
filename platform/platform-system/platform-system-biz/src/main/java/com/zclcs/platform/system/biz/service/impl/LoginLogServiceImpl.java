package com.zclcs.platform.system.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.datasource.starter.base.BasePage;
import com.zclcs.common.datasource.starter.utils.BaseQueryWrapperUtil;
import com.zclcs.platform.system.api.entity.LoginLog;
import com.zclcs.platform.system.api.entity.ao.LoginLogAo;
import com.zclcs.platform.system.api.entity.vo.LoginLogVo;
import com.zclcs.platform.system.biz.mapper.LoginLogMapper;
import com.zclcs.platform.system.biz.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        BaseQueryWrapperUtil.likeNotBlank(queryWrapper, "sll.username", loginLogVo.getUsername());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginLog createLoginLog(LoginLogAo loginLogAo) {
        LoginLog loginLog = new LoginLog();
        BeanUtil.copyProperties(loginLogAo, loginLog);
        this.save(loginLog);
        return loginLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginLog updateLoginLog(LoginLogAo loginLogAo) {
        LoginLog loginLog = new LoginLog();
        BeanUtil.copyProperties(loginLogAo, loginLog);
        this.updateById(loginLog);
        return loginLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLoginLog(List<Long> ids) {
        this.removeByIds(ids);
    }
}
