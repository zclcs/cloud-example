package com.zclcs.platform.system.api.fegin;

import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.core.constant.ServiceNameConstant;
import com.zclcs.platform.system.api.entity.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteRoleService", value = ServiceNameConstant.PLATFORM_SYSTEM_SERVICE)
public interface RemoteRoleService {

    /**
     * 通过角色id查询角色
     *
     * @param roleId 角色id
     * @return BaseRsp
     */
    @GetMapping(value = "/role/findByRoleId/{roleId}", headers = SecurityConstant.HEADER_FROM_IN)
    Role findByRoleId(@PathVariable("roleId") Long roleId);

}
