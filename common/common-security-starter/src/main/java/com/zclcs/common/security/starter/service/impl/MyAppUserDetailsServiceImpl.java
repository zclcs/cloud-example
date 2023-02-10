package com.zclcs.common.security.starter.service.impl;

import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.security.starter.entity.SecurityUser;
import com.zclcs.common.security.starter.service.MyUserDetailsService;
import com.zclcs.platform.system.api.entity.vo.UserVo;
import com.zclcs.platform.system.api.fegin.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
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

        BaseRsp<UserVo> result = remoteUserService.infoByMobile(phone);

        return getUserDetails(result);
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
        return SecurityConstant.APP.equals(grantType);
    }

}
