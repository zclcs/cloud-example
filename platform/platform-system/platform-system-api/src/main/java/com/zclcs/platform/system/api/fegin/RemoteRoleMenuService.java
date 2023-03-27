package com.zclcs.platform.system.api.fegin;

import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.SecurityConstant;
import com.zclcs.common.core.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zclcs
 */
@FeignClient(contextId = "remoteRoleMenuService", value = ServiceNameConstant.PLATFORM_SYSTEM_SERVICE)
public interface RemoteRoleMenuService {

    /**
     * 根据角色id查询菜单id
     *
     * @param roleId 角色id
     * @return BaseRsp
     */
    @GetMapping(value = "/role/menu/findByRoleId/{roleId}", headers = SecurityConstant.HEADER_FROM_IN)
    BaseRsp<List<Long>> findByRoleId(@PathVariable("roleId") Long roleId);

}
