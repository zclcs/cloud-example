package com.zclcs.platform.system.api.fegin;

import com.zclcs.common.core.constant.Security;
import com.zclcs.common.core.constant.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteRoleMenuService", value = ServiceName.PLATFORM_SYSTEM_SERVICE)
public interface RemoteRoleMenuService {

    /**
     * 根据角色id查询菜单id
     *
     * @param roleId 角色id
     * @return BaseRsp
     */
    @GetMapping(value = "/role/menu/findByRoleId/{roleId}", headers = Security.HEADER_FROM_IN)
    List<Long> findByRoleId(@PathVariable("roleId") Long roleId);

}
