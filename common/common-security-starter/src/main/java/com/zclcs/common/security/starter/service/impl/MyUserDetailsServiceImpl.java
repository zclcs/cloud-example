package com.zclcs.common.security.starter.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.security.starter.service.MyUserDetailsService;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import com.zclcs.platform.system.api.fegin.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final RemoteUserService remoteUserService;

    private final ObjectMapper objectMapper;

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        BaseRsp<UserVo> info = remoteUserService.info(username);
        return getUserDetails(info);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
