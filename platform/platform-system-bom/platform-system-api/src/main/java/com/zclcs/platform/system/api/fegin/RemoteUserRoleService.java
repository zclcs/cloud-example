package com.zclcs.platform.system.api.fegin;

import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.core.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteUserRoleService", value = ServiceNameConstant.PLATFORM_SYSTEM_SERVICE)
public interface RemoteUserRoleService {

    /**
     * 根据用户id查询角色id
     *
     * @param userId 用户id
     * @return BaseRsp
     */
    @GetMapping(value = "/user/role/findByUserId/{userId}", headers = SecurityConstant.HEADER_FROM_IN)
    List<Long> findByUserId(@PathVariable("userId") Long userId);

}
