package com.zclcs.platform.system.api.fegin;

import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.constant.ServiceName;
import com.zclcs.platform.system.api.bean.cache.UserCacheVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteUserService", value = ServiceName.PLATFORM_SYSTEM_SERVICE)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return {@link UserCacheVo}
     */
    @GetMapping(value = "/user/findByUsername/{username}", headers = Security.HEADER_FROM_IN)
    UserCacheVo findByUsername(@PathVariable("username") String username);

    /**
     * 通过手机号查询用户名
     *
     * @param mobile 手机号
     * @return BaseRsp
     */
    @GetMapping(value = "/user/findByMobile/{mobile}", headers = Security.HEADER_FROM_IN)
    String findByMobile(@PathVariable("mobile") String mobile);

}
