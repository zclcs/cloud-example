package com.zclcs.platform.system.api.fegin;

import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.core.constant.ServiceNameConstant;
import com.zclcs.platform.system.api.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstant.PLATFORM_SYSTEM_SERVICE)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return BaseRsp
     */
    @GetMapping(value = "/user/findByUsername/{username}", headers = SecurityConstant.HEADER_FROM_IN)
    BaseRsp<User> findByUsername(@PathVariable("username") String username);

    /**
     * 通过手机号查询用户名
     *
     * @param mobile 手机号
     * @return BaseRsp
     */
    @GetMapping(value = "/user/findByMobile/{mobile}", headers = SecurityConstant.HEADER_FROM_IN)
    BaseRsp<String> findByMobile(@PathVariable("mobile") String mobile);

}
