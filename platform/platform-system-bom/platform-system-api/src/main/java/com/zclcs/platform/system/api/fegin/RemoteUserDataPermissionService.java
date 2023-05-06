package com.zclcs.platform.system.api.fegin;

import com.zclcs.cloud.lib.core.constant.Security;
import com.zclcs.cloud.lib.core.constant.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteUserDataPermissionService", value = ServiceName.PLATFORM_SYSTEM_SERVICE)
public interface RemoteUserDataPermissionService {

    /**
     * 根据用户id查询权限编码id
     *
     * @param userId 用户id
     * @return BaseRsp
     */
    @GetMapping(value = "/user/data/permission/findByUserId/{userId}", headers = Security.HEADER_FROM_IN)
    List<Long> findByUserId(@PathVariable("userId") Long userId);

}
