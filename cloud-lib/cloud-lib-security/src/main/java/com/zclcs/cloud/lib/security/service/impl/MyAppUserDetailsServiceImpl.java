package com.zclcs.cloud.lib.security.service.impl;

import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.security.entity.SecurityUser;
import com.zclcs.cloud.lib.security.service.MyUserDetailsService;
import com.zclcs.platform.system.api.bean.vo.UserVo;
import com.zclcs.platform.system.api.fegin.RemoteUserService;
import com.zclcs.platform.system.utils.SystemCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author zclcs
 */
@Slf4j
@RequiredArgsConstructor
public class MyAppUserDetailsServiceImpl implements MyUserDetailsService {

    private final RemoteUserService remoteUserService;

    /**
     * 手机号登录
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String phone) {

        UserVo userByMobile = SystemCacheUtil.getUserByMobile(phone);

        return getUserDetails(userByMobile);
    }

    /**
     * check-token 使用
     *
     * @param securityUser user
     * @return
     */
    @Override
    public UserDetails loadUserByUser(SecurityUser securityUser) {
        return this.loadUserByUsername(securityUser.getPhone());
    }

    /**
     * 是否支持此客户端校验
     *
     * @param clientId 目标客户端
     * @return true/false
     */
    @Override
    public boolean support(String clientId, String grantType) {
        return Security.APP.equals(grantType);
    }

}
