package com.zclcs.cloud.lib.security.service.impl;

import com.zclcs.cloud.lib.security.service.MyUserDetailsService;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author zclcs
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserVo userByUsername = SystemCacheUtil.getUserByUsername(username);
        return getUserDetails(userByUsername);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
